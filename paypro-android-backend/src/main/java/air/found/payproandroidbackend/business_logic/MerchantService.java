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
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MerchantService {
    public static final String MERCHANT_NAME_REGEX = "^[A-Za-z0-9 \\$\\-\\&\\'@_]{2,50}$";
    private static final Pattern MERCHANT_NAME_PATTERN = Pattern.compile(MERCHANT_NAME_REGEX);

    private final MerchantRepository merchantsRepository;

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

    public ServiceResult<Boolean> updateMerchant(Integer id, Merchant merchant) {
        if(!isValidName(merchant.getMerchantName())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_MERCHANT_NAME);
        }
        
        if(merchantExists(merchant.getMerchantName())) {
            return ServiceResult.failure(ApiError.ERR_MERCHANT_ALREADY_EXISTS);
        }

        ServiceResult<Boolean> cardBrandsResult = updateAcceptedCards(merchant);
        if (!cardBrandsResult.isSuccess()) {
            return cardBrandsResult;
        }

        ServiceResult<Boolean> statusResult = updateStatus(merchant);
        if (!statusResult.isSuccess()) {
            return statusResult;
        }

        try {
            merchant.setId(id);
            merchantsRepository.save(merchant);
            return ServiceResult.success();
        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }
    }

    private ServiceResult<Boolean> updateAcceptedCards(Merchant merchant) {
        Set<CardBrand> cardBrands = new HashSet<>();

        for (Integer cardId : merchant.getAcceptedCards()) {
            CardBrandType cardBrandType = getCardBrandTypeById(cardId);
            if (cardBrandType == null) {
                return ServiceResult.failure(ApiError.ERR_INVALID_ACCEPTED_CARDS); // New error for invalid card brand
            }
            cardBrands.add(new CardBrand(cardBrandType.getId(), cardBrandType.getName()));
        }

        merchant.setAcceptedCardsEnum(cardBrands);
        return ServiceResult.success();
    }

    private ServiceResult<Boolean> updateStatus(Merchant merchant) {
        StatusType statusType = getStatusTypeById(merchant.getStatus());
        if (statusType == null) {
            return ServiceResult.failure(ApiError.ERR_INVALID_STATUS); // New error for invalid status
        }

        Status status = new Status(statusType.getId(), statusType.getName());
        merchant.setStatusEnum(status);
        return ServiceResult.success();
    }

    private boolean merchantExists(String merchantName) {
        return merchantsRepository.existsByMerchantName(merchantName);
    }

    private boolean isValidName(String merchantName) {
        if (merchantName == null) {
            return false;
        }

        return MERCHANT_NAME_PATTERN.matcher(merchantName).matches();
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
