package air.found.payproandroidbackend.endpoints.controllers;


import air.found.payproandroidbackend.business_logic.MerchantService;
import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.CardBrand;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import air.found.payproandroidbackend.core.network.ResponseBody;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseBody<List<Merchant>>> getMerchantsByUserAccount(@PathVariable Integer userId) {
        ServiceResult<List<Merchant>> result = merchantService.getMerchantsByUser(userId);
        return respond(result, "Merchants fetched successfully");
    }

    @DeleteMapping("/{merchantId}")
    public ResponseEntity<ResponseBody<Void>> deleteMerchant(@PathVariable Integer merchantId) {
        ServiceResult<Void> result = merchantService.deleteMerchantById(merchantId);
        return respond(result, "Merchant deleted successfully");
    }

    @PostMapping
    public ResponseEntity<ResponseBody<Merchant>> addMerchant(@RequestBody Merchant merchant) {
        ServiceResult<Merchant> result = merchantService.saveMerchant(merchant);
        return respond(result, "Merchant added successfully");
    }

    @PutMapping("/{merchantId}")
    public ResponseEntity<ResponseBody<Merchant>> updateMerchant(@PathVariable Integer merchantId, @RequestBody Merchant merchant) {
        ServiceResult<Merchant> result = merchantService.updateMerchant(merchantId, merchant);
        return respond(result, "Merchant updated successfully");
    }

    @GetMapping("/{merchantId}/card-brands")
    public ResponseEntity<ResponseBody<Set<CardBrand>>> getAcceptedCardBrandsForMerchant(@PathVariable Integer merchantId) {
        ServiceResult<Set<CardBrand>> result = merchantService.getAcceptedCardBrands(merchantId);
        return respond(result, "Card brands fetched successfully");
    }

    private <T> ResponseEntity<ResponseBody<T>> respond(ServiceResult<T> result, String successMessage) {
        if (result.isSuccess()) {
            return ApiResponseBuilder.buildSuccessResponse(result.getData(), successMessage);
        } else {
            ApiError apiError = result.getApiError();
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST, apiError.getErrorMessage(), apiError.getErrorCode(), apiError.getErrorName());
        }
    }
}
