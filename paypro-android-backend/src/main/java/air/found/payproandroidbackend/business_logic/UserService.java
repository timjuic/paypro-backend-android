package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.models.UserAccount;
import air.found.payproandroidbackend.data_access.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public boolean registerUser(UserAccount userAccount) {
        try {
            userRepository.save(userAccount);
          return true;
        }
        catch (Exception ex) {
            return false;
        }
    }
}
