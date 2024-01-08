package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.CardBrand;
import air.found.payproandroidbackend.data_access.persistence.CardBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardBrandService {

    @Autowired
    private CardBrandRepository cardBrandRepository; // You need to create this repository interface.

    public ServiceResult<List<CardBrand>> getAllCardBrands() {
        List<CardBrand> cardBrandList = cardBrandRepository.findAll();
        return ServiceResult.success(cardBrandList);
    }
}