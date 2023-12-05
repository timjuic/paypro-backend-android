package air.found.payproandroidbackend.endpoints.controllers;

import air.found.payproandroidbackend.business_logic.UserService;
import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.UserAccount;
import air.found.payproandroidbackend.core.network.ApiResponseBuilder;
import air.found.payproandroidbackend.core.network.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/register")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    public ResponseEntity<ResponseBody<Object>> registration(@RequestBody UserAccount userAccount) {
        ServiceResult<Boolean> result = userService.registerUser(userAccount);

        if (result.isSuccess()) {
            return ApiResponseBuilder.buildSuccessResponse(null, "You have been successfully registered!");
        }

        ApiError apiError = result.getApiError();
        return ApiResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST, apiError.getErrorMessage(), apiError.getErrorCode(), apiError.getErrorName());
    }
}
