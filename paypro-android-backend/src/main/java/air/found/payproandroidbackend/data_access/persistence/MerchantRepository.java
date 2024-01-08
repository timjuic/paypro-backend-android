package air.found.payproandroidbackend.data_access.persistence;

import air.found.payproandroidbackend.core.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    boolean existsByMerchantName(String name);

    @Query("SELECT DISTINCT m FROM Merchant m " +
            "JOIN FETCH m.terminals t " +
            "JOIN FETCH m.userAccounts u " +
            "WHERE u.userId = :userId")
    List<Merchant> findAllByUserId(@Param("userId") Integer userId);
}