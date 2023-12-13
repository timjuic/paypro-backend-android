package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.PasswordHashing;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.UserAccount;
import air.found.payproandroidbackend.data_access.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private final UserRepository userRepository;
    private final PasswordHashing passwordHashing;

    public ServiceResult<UserAccount> loginUser(UserAccount userAccount) {
        if (isInvalidEmailFormat(userAccount.getEmailAddress())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_EMAIL_FORMAT);
        }

        UserAccount userFromDb = userRepository.findByEmailAddress(userAccount.getEmailAddress());
        if (userFromDb == null) {
            return ServiceResult.failure(ApiError.ERR_USER_NOT_FOUND);
        }

        boolean passwordsMatch = passwordHashing.checkPassword(userAccount.getPassword(), userFromDb.getPassword());
        if (!passwordsMatch) {
            return ServiceResult.failure(ApiError.ERR_PASSWORDS_DONT_MATCH);
        }

        return ServiceResult.success();
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

        if (userRepository.findByEmailAddress(userAccount.getEmailAddress()) != null) {
            return ServiceResult.failure(ApiError.ERR_EMAIL_ALREADY_IN_USE);
        }

        try {
            userAccount.setPassword(passwordHashing.hashPassword(userAccount.getPassword()));
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
