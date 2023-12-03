package air.found.payproandroidbackend.endpoints.controllers;


import air.found.payproandroidbackend.business_logic.MerchantService;
import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.CardBrand;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import air.found.payproandroidbackend.core.network.ResponseBody;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody<List<Merchant>>> getMerchantsByUserAccount(@PathVariable("userId") Integer userId) {
        List<Merchant> merchants = merchantService.getMerchantsByUser(userId);

        if (merchants.isEmpty()) {
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, "No merchants found for the user", 2, "ERR_NO_MERCHANTS_FOUND");
        } else {
            return ApiResponseBuilder.buildSuccessResponse(merchants, "Merchants successfully retrieved");
        }
    }

    @DeleteMapping("/{id}")
    public <T> ResponseEntity<ResponseBody<T>> deleteMerchant(@PathVariable Integer id) {
        boolean deletionResult = merchantService.deleteMerchantById(id);

        if (deletionResult) {
            return ApiResponseBuilder.buildSuccessResponse(null, "Merchant successfully deleted!");
        } else {
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, "Merchant not found", 1, "ERR_MERCHANT_NOT_FOUND");
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseBody<Merchant>> addMerchant(@RequestBody Merchant merchant) {
        boolean result = merchantService.saveMerchant(merchant);

        if (result) {
            return ApiResponseBuilder.buildSuccessResponse(null, "Merchant successfully added!");
        } else {
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST, "Merchant not added", 1, "ERR_MERCHANT_NOT_ADDED");
        }
    }

    @GetMapping("/{mid}/card-brands")
    public ResponseEntity<ResponseBody<Set<CardBrand>>> getAcceptedCardBrandsForMerchant(@PathVariable("mid") Integer merchantId) {
        ServiceResult<Set<CardBrand>> serviceResult = merchantService.getAcceptedCardBrands(merchantId);
        if (!serviceResult.isSuccess()) {
            ApiError apiError = serviceResult.getApiError();
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, apiError.getErrorMessage(), apiError.getErrorCode(), apiError.getErrorName());
        }

        Set<CardBrand> cardBrands = serviceResult.getData();
        return ApiResponseBuilder.buildSuccessResponse(cardBrands, "Card brands for merchant with ID " + merchantId + " successfully retrieved!");
    }
}
