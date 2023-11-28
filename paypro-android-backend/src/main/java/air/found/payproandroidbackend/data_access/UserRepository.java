package air.found.payproandroidbackend.data_access;

import air.found.payproandroidbackend.core.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Integer> {
}
