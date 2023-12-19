package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.UserAccount;
import air.found.payproandroidbackend.data_access.persistence.UserRepository;
import com.google.api.client.json.webtoken.JsonWebToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final GoogleTokenVerifierService googleTokenVerifierService;

    public ServiceResult<UserAccount> loginUser(UserAccount userAccount) {
        if (isInvalidEmailFormat(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_EMAIL_FORMAT);
        }

        Optional<UserAccount> checkGoogle = userRepository.findByEmailAddress(userAccount.getEmailAddress());
        if(checkGoogle.isPresent() && checkGoogle.get().getIsGoogle()) {
            return ServiceResult.failure(ApiError.ERR_USE_GOOGLE_LOGIN);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAccount.getEmailAddress(), userAccount.getPassword()));
        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_CREDENTIALS);
        }

        return userRepository.findByEmailAddress(userAccount.getEmailAddress())
                .map(ServiceResult::success)
                .orElse(ServiceResult.failure(ApiError.ERR_INVALID_CREDENTIALS));
    }

    public ServiceResult<Void> registerUser(UserAccount userAccount) {
        if (isInvalidEmailFormat(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_EMAIL_FORMAT);
        }

        if (userAccount.getPassword().length() < 8) {
            return ServiceResult.failure(ApiError.ERR_PASSWORD_TOO_WEAK);
        }

        if (userRepository.findByEmailAddress(userAccount.getEmailAddress()).isPresent()) {
            return ServiceResult.failure(ApiError.ERR_EMAIL_ALREADY_IN_USE);
        }

        try {
            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            userRepository.save(userAccount);
            return ServiceResult.success();
        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }
    }

    public ServiceResult<UserAccount> google(String idToken) {
        try {
            Payload payload = googleTokenVerifierService.verifyToken(idToken);
            Optional<UserAccount> userAccount = userRepository.findByEmailAddress(payload.getEmail());
            if(userAccount.isEmpty()) {
                UserAccount newUser = new UserAccount();
                newUser.setEmailAddress(payload.getEmail());
                newUser.setFirstName((String) payload.get("given_name"));
                newUser.setLastName((String) payload.get("family_name"));
                newUser.setPassword("");
                newUser.setIsGoogle(true);
                userRepository.save(newUser);
            }
            UserAccount dbUser = userRepository.findByEmailAddress(payload.getEmail()).isPresent() ? userRepository.findByEmailAddress(payload.getEmail()).get() : null;

            if(dbUser != null){
                return ServiceResult.success(dbUser);
            } else {
                return ServiceResult.failure(ApiError.ERR_USER_NOT_FOUND);
            }
        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_GOOGLE_TOKEN);
        }
    }

    private boolean isInvalidEmailFormat(String email) {
       return email == null || !EMAIL_PATTERN.matcher(email).matches();
    }
}
