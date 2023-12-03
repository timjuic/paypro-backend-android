package air.found.payproandroidbackend.data_access.persistence;

import air.found.payproandroidbackend.core.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
}
