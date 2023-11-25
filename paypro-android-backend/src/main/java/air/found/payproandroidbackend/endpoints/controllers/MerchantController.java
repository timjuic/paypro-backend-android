package air.found.payproandroidbackend.endpoints.controllers;


import air.found.payproandroidbackend.business_logic.MerchantService;
import air.found.payproandroidbackend.core.models.Merchants;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import air.found.payproandroidbackend.core.network.ResponseBody;


import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
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
}
