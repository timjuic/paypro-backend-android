package air.found.payproandroidbackend.data_access.persistence;

import air.found.payproandroidbackend.core.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
    boolean existsByMerchantName(String name);
}