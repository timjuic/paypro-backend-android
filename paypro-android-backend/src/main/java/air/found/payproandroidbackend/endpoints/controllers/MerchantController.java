package air.found.payproandroidbackend.endpoints.controllers;


import air.found.payproandroidbackend.core.models.Merchants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import air.found.payproandroidbackend.core.network.ResponseBody;


import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<List<Merchants>>> getMerchant(@PathVariable Long id) {

        ResponseBody<List<Merchants>> response = (ResponseBody<List<Merchants>>) ResponseBody.error("API not implemented", 999, "ERR_NOT_IMPLEMENTED");
        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED);
    }


}
