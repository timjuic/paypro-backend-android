package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.enums.CardBrandType;
import air.found.payproandroidbackend.core.enums.StatusType;
import air.found.payproandroidbackend.core.models.CardBrand;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.models.Status;
import air.found.payproandroidbackend.data_access.CardBrandRepository;
import air.found.payproandroidbackend.data_access.MerchantRepository;
import air.found.payproandroidbackend.data_access.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MerchantService {
    private final MerchantRepository merchantsRepository;
    private CardBrandRepository cardBrandRepository;
    private StatusRepository statusRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantsRepository) {
        this.merchantsRepository = merchantsRepository;
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
