package air.found.payproandroidbackend.data_access;

import air.found.payproandroidbackend.core.models.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
}
