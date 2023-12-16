package air.found.payproandroidbackend.data_access.persistence;

import air.found.payproandroidbackend.core.models.UserAccount;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Integer> {
    Optional<UserAccount> findByEmailAddress(String email);
}
