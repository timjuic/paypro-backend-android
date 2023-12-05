package air.found.payproandroidbackend.data_access.persistence;

import air.found.payproandroidbackend.core.models.CardBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardBrandRepository extends JpaRepository<CardBrand, Integer> {}
