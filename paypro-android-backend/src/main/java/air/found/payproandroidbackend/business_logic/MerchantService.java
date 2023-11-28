package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.data_access.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class MerchantService {
    private final MerchantRepository merchantsRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantsRepository) {
        this.merchantsRepository = merchantsRepository;
    }


    public List<Merchant> getMerchantsByUser(Long userId) {
        return merchantsRepository.findMerchantsByUserId(userId);
    }

    public boolean deleteMerchantById(Integer merchantId) {
        Optional<Merchant> merchantOptional = merchantsRepository.findById(merchantId);

        if (merchantOptional.isPresent()) {
            merchantsRepository.deleteById(merchantId);
            return true;
        } else {
            return false;
        }
    }
}
