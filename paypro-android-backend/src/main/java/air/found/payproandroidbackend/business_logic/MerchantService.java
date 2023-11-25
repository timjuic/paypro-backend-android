package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.models.Merchants;
import air.found.payproandroidbackend.data_access.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService {
    private final MerchantRepository merchantsRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantsRepository) {
        this.merchantsRepository = merchantsRepository;
    }

    public boolean deleteMerchantById(Integer merchantId) {
        Optional<Merchants> merchantOptional = merchantsRepository.findById(merchantId);

        if (merchantOptional.isPresent()) {
            merchantsRepository.deleteById(merchantId);
            return true;
        } else {
            return false;
        }
    }
}
