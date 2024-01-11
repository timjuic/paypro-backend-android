package air.found.payproandroidbackend.core;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordHashing {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Hashes a password.
     *
     * @param password the password to hash
     * @return the hashed password
     */
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Checks a password against a hashed password.
     *
     * @param rawPassword     the raw password to check
     * @param encodedPassword the hashed password
     * @return true if the password match, false otherwise
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
