package air.found.payproandroidbackend.endpoints.controllers;

import air.found.payproandroidbackend.business_logic.TerminalService;
import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.Terminal;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import air.found.payproandroidbackend.core.network.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

        ServiceResult<Boolean> serviceResult = terminalService.addTerminalToMerchant(merchantId, terminal);

        if (!serviceResult.isSuccess()) {
            ApiError apiError = serviceResult.getApiError();
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, apiError.getErrorMessage(), apiError.getErrorCode(), apiError.getErrorName());
        } else {
            return ApiResponseBuilder.buildSuccessResponse(null, "Terminal successfully added");
        }
    }

    @GetMapping("")
    public ResponseEntity<ResponseBody<Object>> getTerminalsForMerchant(@PathVariable("mid") Integer merchantId) {
        ServiceResult<List<Terminal>> serviceResult = terminalService.getTerminalsForMerchant(merchantId);

        if (!serviceResult.isSuccess()) {
            ApiError apiError = serviceResult.getApiError();
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.NOT_FOUND, apiError.getErrorMessage(), apiError.getErrorCode(), apiError.getErrorName());
        } else {
            List<Terminal> terminalList = serviceResult.getData();
            return ApiResponseBuilder.buildSuccessResponse(terminalList, "Terminals for merchant successfully retrieved");
        }

    }
}
