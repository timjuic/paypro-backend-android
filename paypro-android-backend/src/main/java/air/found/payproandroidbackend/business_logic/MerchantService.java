package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.enums.CardBrandType;
import air.found.payproandroidbackend.core.enums.StatusType;
import air.found.payproandroidbackend.core.models.CardBrand;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.models.Status;
import air.found.payproandroidbackend.core.models.UserAccount;
import air.found.payproandroidbackend.data_access.manual.MerchantEntityRepository;
import air.found.payproandroidbackend.data_access.manual.UserMerchantsRepository;
import air.found.payproandroidbackend.data_access.persistence.MerchantRepository;
import air.found.payproandroidbackend.data_access.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private static final Pattern MERCHANT_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9 \\$\\-\\&\\'@_]{2,50}$");
    private final MerchantRepository merchantsRepository;
    private final UserRepository userRepository;
    private final MerchantEntityRepository merchantEntityRepository;
    private final UserMerchantsRepository userMerchantsRepository;

    public ServiceResult<List<Map<String, Object>>> getMerchantsByUser(Integer userId) {
        if(userRepository.findById(userId).isEmpty()) {
            return ServiceResult.failure(ApiError.ERR_USER_NOT_FOUND);
        }
        List<Map<String, Object>> merchants = merchantEntityRepository.findMerchantsAndTerminalsByUserId(userId);
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

    public ServiceResult<Merchant> saveMerchant(Merchant merchant, Integer userId) {
        ServiceResult<Merchant> validationResult = validateMerchant(merchant);
        if (!validationResult.isSuccess()) {
            return validationResult;
        }

        try {
            Optional<UserAccount> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) return ServiceResult.failure(ApiError.ERR_USER_NOT_FOUND);

            Merchant savedMerchant = merchantsRepository.save(merchant);

            UserAccount user = userOptional.get();
            userMerchantsRepository.addUserMerchantRelationship(user.getUserId(), savedMerchant.getId());

            return ServiceResult.success(savedMerchant);

        } catch (Exception ex) {
            System.out.println(ex);
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }
    }



    public ServiceResult<Merchant> updateMerchant(Integer id, Merchant merchant) {
        ServiceResult<Merchant> validationResult = validateMerchant(merchant);
        if(!validationResult.isSuccess()) {
            return validationResult;
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

    private ServiceResult<Merchant> validateMerchant(Merchant merchant) {
        if (isInvalidName(merchant.getMerchantName())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_MERCHANT_NAME);
        }
        if (merchantsRepository.existsByMerchantName(merchant.getMerchantName())) {
            return ServiceResult.failure(ApiError.ERR_MERCHANT_ALREADY_EXISTS);
        }
        if (merchant.getAcceptedCards().isEmpty()) {
            return ServiceResult.failure(ApiError.ERR_ACCEPTED_CARDS_NOT_DEFINED);
        }
        if (merchant.getStatus().getStatusId() == null) {
            return ServiceResult.failure(ApiError.ERR_STATUS_NOT_DEFINED);
        }
        if (!isValidCardBrands(merchant.getAcceptedCards())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_ACCEPTED_CARDS);
        }
        if (!isValidStatus(merchant.getStatus())) {
            return ServiceResult.failure(ApiError.ERR_INVALID_STATUS);
        }
        if (!(merchant.getAddress().getStreetName().length() >= 2 && merchant.getAddress().getStreetName().length() <= 100) ||
                (merchant.getAddress().getStreetNumber().isEmpty() || merchant.getAddress().getStreetName().length() > 10)) {
            return ServiceResult.failure(ApiError.ERR_INVALID_INPUT);
        }

        return ServiceResult.success(null); // Return success with no data if validation passes
    }

    private boolean isValidStatus(Status status) {
        Set<String> validNames = Arrays.stream(StatusType.values())
                .map(StatusType::getName)
                .collect(Collectors.toSet());

        return validNames.contains(status.getStatusName());
    }

    private boolean isValidCardBrands(Set<CardBrand> cardBrands) {
        Set<String> validNames = Arrays.stream(CardBrandType.values())
                .map(CardBrandType::getName)
                .collect(Collectors.toSet());

        return cardBrands.stream()
                .allMatch(cardBrand -> validNames.contains(cardBrand.getName()));
    }

    private boolean isInvalidName(String merchantName) {
        return merchantName == null || !MERCHANT_NAME_PATTERN.matcher(merchantName).matches();
    }
}
