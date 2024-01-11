package air.found.payproandroidbackend.endpoints.controllers;

import air.found.payproandroidbackend.business_logic.CardBrandService;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.CardBrand;
import air.found.payproandroidbackend.core.models.Merchant;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import air.found.payproandroidbackend.core.network.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/card-brands")
public class CardBrandController {

    @Autowired
    private CardBrandService cardBrandService;

    @GetMapping
    public ResponseEntity<ResponseBody<List<CardBrand>>> getAllCardBrands() {
        ServiceResult<List<CardBrand>> result = cardBrandService.getAllCardBrands();
        return ApiResponseBuilder.buildSuccessResponse(result.getData(), "Card brands list successfully retrieved!");
    }
}