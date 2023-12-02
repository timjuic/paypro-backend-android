package air.found.payproandroidbackend.data_access.persistence;

import air.found.payproandroidbackend.core.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {}