package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.UserAccount;
import air.found.payproandroidbackend.data_access.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmailAddress(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    public ServiceResult<UserAccount> loginUser(UserAccount userAccount) {
        if (isInvalidEmailFormat(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_EMAIL_FORMAT);
        }

        return userRepository.findByEmailAddressAndPassword(userAccount.getEmailAddress(), userAccount.getPassword())
                .map(ServiceResult::success)
                .orElse(ServiceResult.failure(ApiError.ERR_INVALID_CREDENTIALS));
    }

    public ServiceResult<Void> registerUser(UserAccount userAccount) {
        if (isInvalidEmailFormat(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_EMAIL_FORMAT);
        }

        if (!userAccount.getPassword().equals(userAccount.getRepeatedPassword())) {
            return ServiceResult.failure(ApiError.ERR_PASSWORDS_DONT_MATCH);
        }

        if (userAccount.getPassword().length() < 8) {
            return ServiceResult.failure(ApiError.ERR_PASSWORD_TOO_WEAK);
        }

        if (userRepository.findByEmailAddress(userAccount.getEmailAddress()).isPresent()) {
            return ServiceResult.failure(ApiError.ERR_EMAIL_ALREADY_IN_USE);
        }

        try {
            userRepository.save(userAccount);
            return ServiceResult.success();
        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }
    }

    private boolean isInvalidEmailFormat(String email) {
       return email == null || !EMAIL_PATTERN.matcher(email).matches();
    }
}
