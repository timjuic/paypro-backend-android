package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.enums.CardBrandType;
import air.found.payproandroidbackend.core.enums.StatusType;
import air.found.payproandroidbackend.core.models.CardBrand;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.models.Status;
import air.found.payproandroidbackend.data_access.persistence.CardBrandRepository;
import air.found.payproandroidbackend.data_access.persistence.MerchantRepository;
import air.found.payproandroidbackend.data_access.persistence.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MerchantService {
    private final MerchantRepository merchantsRepository;
    private CardBrandRepository cardBrandRepository;
    private StatusRepository statusRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantsRepository) {
        this.merchantsRepository = merchantsRepository;
    }

    public List<Merchant> getMerchantsByUser(Integer userId) {
        return air.found.payproandroidbackend.data_access.manual.MerchantRepository.getMerchantsByUser(userId);
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

    public boolean saveMerchant(Merchant merchant) {
        try{
            if(merchant.getAcceptedCards() != null) {
                Set<CardBrand> cardBrands = new HashSet<>();

                for(Integer id : merchant.getAcceptedCards()) {
                    CardBrandType cardBrandType = getCardBrandTypeById(id);
                    if(cardBrandType != null) {
                        CardBrand cardBrand = new CardBrand(id, cardBrandType.getName());
                        cardBrands.add(cardBrand);
                    }
                }

                merchant.setAcceptedCardsEnum(cardBrands);
            }

            if(merchant.getStatus() != null) {
                StatusType statusType = getStatusTypeById(merchant.getStatus());
                if(statusType != null) {
                    Status status = new Status(statusType.getId(), statusType.getName());
                    merchant.setStatusEnum(status);
                }
            }

            merchantsRepository.save(merchant);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public ServiceResult<Set<CardBrand>> getAcceptedCardBrands(Integer merchantId) {
        Optional<Merchant> merchantOptional = merchantsRepository.findById(merchantId);

        if (merchantOptional.isEmpty()) {
            return ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND);
        }

        Merchant merchant = merchantOptional.get();
        return ServiceResult.success(merchant.getAcceptedCardsEnum());
    }


    private StatusType getStatusTypeById(Integer id) {
        for(StatusType type : StatusType.values()) {
            if(type.getId() == id) {
                return type;
            }
        }

        return null;
    }

    private CardBrandType getCardBrandTypeById(Integer id) {
        for(CardBrandType type : CardBrandType.values()) {
            if(type.getId() == id) {
                return type;
            }
        }

        return null;
    }
}
