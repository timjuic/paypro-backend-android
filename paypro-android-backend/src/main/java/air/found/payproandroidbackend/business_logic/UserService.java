package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.JwtTokenInfo;
import air.found.payproandroidbackend.core.models.UserAccount;
import air.found.payproandroidbackend.data_access.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ServiceResult<UserAccount> loginUser(UserAccount userAccount) {
        if (!isValidEmailFormat(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_EMAIL_FORMAT);
        }

        try {
            Optional<UserAccount> user = userRepository.findByEmailAddressAndPassword(userAccount.getEmailAddress(), userAccount.getPassword());
            return user.map(ServiceResult::success).orElseGet(() -> ServiceResult.failure(ApiError.ERR_INVALID_CREDENTIALS));

        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }
    }

    public ServiceResult<Boolean> registerUser(UserAccount userAccount) {
        if (!isValidEmailFormat(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_EMAIL_FORMAT);
        }

        if (!Objects.equals(userAccount.getPassword(), userAccount.getRepeatedPassword())) {
            return ServiceResult.failure(ApiError.ERR_PASSWORDS_DONT_MATCH);
        }

        if (userAccount.getPassword().length() < 8) {
            return ServiceResult.failure(ApiError.ERR_PASSWORD_TOO_WEAK);
        }

        if (emailExists(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_EMAIL_ALREADY_IN_USE);
        }

        try {
            userRepository.save(userAccount);
            return ServiceResult.success();
        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }
    }

    private boolean emailExists(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).isPresent();
    }

    private boolean isValidEmailFormat(String email) {
        if (email == null) {
            return false;
        }

        return EMAIL_PATTERN.matcher(email).matches();
    }
}
