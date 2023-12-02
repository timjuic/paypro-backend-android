package air.found.payproandroidbackend.endpoints.controllers;

import air.found.payproandroidbackend.business_logic.TerminalService;
import air.found.payproandroidbackend.core.models.Terminal;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import air.found.payproandroidbackend.core.network.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/merchant/{mid}/terminal")
public class TerminalController {

    private final TerminalService terminalService;

    @Autowired
    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseBody<Object>> addTerminal(@PathVariable("mid") Integer merchantId, @RequestBody Terminal terminal) {

        Boolean successfullyAdded = terminalService.addTerminalToMerchant(merchantId, terminal);

        if (!successfullyAdded) {
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, "Adding terminal has failed. Please check the request body", 2, "ERR_");
        } else {
            return ApiResponseBuilder.buildSuccessResponse(true, "Terminal successfully added");
        }
    }
}
