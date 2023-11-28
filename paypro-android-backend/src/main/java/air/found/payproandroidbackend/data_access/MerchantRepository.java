package air.found.payproandroidbackend.data_access;

import air.found.payproandroidbackend.core.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

    // Assuming there's a UserMerchants entity representing the relationship
    @Query("SELECT um.merchant FROM UserMerchant um WHERE um.userAccount.userId = :userId")
    List<Merchant> findMerchantsByUserId(Long userId);
}
