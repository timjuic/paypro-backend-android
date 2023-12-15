package air.found.payproandroidbackend.endpoints.controllers;

import air.found.payproandroidbackend.business_logic.TerminalService;
import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.Terminal;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import air.found.payproandroidbackend.core.network.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/merchant/{mid}/terminal")
public class TerminalController {
    private final TerminalService terminalService;

    @PostMapping
    public ResponseEntity<ResponseBody<Void>> addTerminal(@PathVariable("mid") Integer merchantId, @RequestBody Terminal terminal) {
        ServiceResult<Void> result = terminalService.addTerminalToMerchant(merchantId, terminal);
        return respond(result, "Terminal successfully added");
    }

    @GetMapping
    public ResponseEntity<ResponseBody<List<Terminal>>> getTerminalsForMerchant(@PathVariable("mid") Integer merchantId) {
        ServiceResult<List<Terminal>> result = terminalService.getTerminalsForMerchant(merchantId);
        return respond(result, "Terminals for merchant successfully retrieved");
    }

    @DeleteMapping("/{tid}")
    public ResponseEntity<ResponseBody<Void>> deleteTerminal(@PathVariable("tid") Integer terminalId, @PathVariable("mid") Integer merchantId) {
        ServiceResult<Void> result = terminalService.deleteTerminal(terminalId, merchantId);
        return respond(result, "Terminal with id " + terminalId + " successfully deleted");
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
