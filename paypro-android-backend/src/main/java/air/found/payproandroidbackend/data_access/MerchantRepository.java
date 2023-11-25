package air.found.payproandroidbackend.data_access;

import air.found.payproandroidbackend.core.models.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchants, Integer> {

}
