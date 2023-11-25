package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.data_access.MerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class MerchantService {
    private final MerchantRepository merchantsRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantsRepository) {
        this.merchantsRepository = merchantsRepository;
    }

    public boolean deleteMerchantById(Integer merchantId) {
        try {
            merchantsRepository.deleteById(merchantId);
            return true; // Deletion successful
        } catch (EmptyResultDataAccessException e) {
            return false; // Merchant not found
        } catch (Exception e) {
            return false; // Error deleting merchant
        }
    }
}
