package air.found.payproandroidbackend.endpoints.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @GetMapping
    public String getMerchant() {
        return "test";
    }
}
