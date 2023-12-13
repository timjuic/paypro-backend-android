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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private static final Pattern MERCHANT_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9 \\$\\-\\&\\'@_]{2,50}$");
    private final MerchantRepository merchantsRepository;

    public ServiceResult<List<Merchant>> getMerchantsByUser(Integer userId) {
        List<Merchant> merchants = air.found.payproandroidbackend.data_access.manual.MerchantRepository.getMerchantsByUser(userId);
        if(merchants.isEmpty()) {
            return ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND);
        }
        return ServiceResult.success(merchants);
    }

    public ServiceResult<Void> deleteMerchantById(Integer merchantId) {
        return merchantsRepository.findById(merchantId)
                .map(merchant -> {
                    merchantsRepository.deleteById(merchantId);
                    return ServiceResult.<Void>success();
                })
                .orElseGet(() -> ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND));
    }

    public ServiceResult<Merchant> saveMerchant(Merchant merchant) {
        if(!isValidName(merchant.getMerchantName())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_MERCHANT_NAME);
        }

        try {
            Merchant savedMerchant = merchantsRepository.save(merchant);
            return ServiceResult.success(savedMerchant);
        } catch (Exception ex) {
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }
    }

    public ServiceResult<Merchant> updateMerchant(Integer id, Merchant merchant) {
        if(!isValidName(merchant.getMerchantName())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_MERCHANT_NAME);
        }
        return merchantsRepository.findById(id)
                .map(existingMerchant -> {
                    merchant.setId(id);
                    merchantsRepository.save(merchant);
                    return ServiceResult.success(merchant);
                })
                .orElseGet(() -> ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND));
    }

    public ServiceResult<Set<CardBrand>> getAcceptedCardBrands(Integer merchantId) {
        return merchantsRepository.findById(merchantId)
                .map(merchant -> ServiceResult.success(merchant.getAcceptedCards()))
                .orElseGet(() -> ServiceResult.failure(ApiError.ERR_MERCHANT_NOT_FOUND));
    }

    private boolean isValidName(String merchantName) {
        return merchantName != null && MERCHANT_NAME_PATTERN.matcher(merchantName).matches();
    }
}
