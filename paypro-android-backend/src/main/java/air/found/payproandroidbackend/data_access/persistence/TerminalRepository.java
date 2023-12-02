package air.found.payproandroidbackend.data_access.persistence;

import air.found.payproandroidbackend.core.models.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
    boolean existsByTerminalKey(String terminalKey);

    List<Terminal> findByMerchantId(Integer merchantId);
}
