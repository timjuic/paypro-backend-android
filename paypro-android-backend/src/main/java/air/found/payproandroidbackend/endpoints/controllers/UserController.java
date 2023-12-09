package air.found.payproandroidbackend.endpoints.controllers;

import air.found.payproandroidbackend.business_logic.TokenService;
import air.found.payproandroidbackend.business_logic.UserService;
import air.found.payproandroidbackend.core.ApiError;
import air.found.payproandroidbackend.core.ServiceResult;
import air.found.payproandroidbackend.core.models.JwtTokenInfo;
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
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) { this.userService = userService; this.tokenService = tokenService; }

    @PostMapping("/register")
    public ResponseEntity<ResponseBody<Object>> registration(@RequestBody UserAccount userAccount) {
        ServiceResult<Boolean> result = userService.registerUser(userAccount);

        if (result.isSuccess()) {
            return ApiResponseBuilder.buildSuccessResponse(null, "You have been successfully registered!");
        }

        ApiError apiError = result.getApiError();
        return ApiResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST, apiError.getErrorMessage(), apiError.getErrorCode(), apiError.getErrorName());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody<Object>> login(@RequestBody UserAccount userAccount) {
        ServiceResult<UserAccount> result = userService.loginUser(userAccount);

        if (result.isSuccess()) {
            JwtTokenInfo token = tokenService.getJwtToken(result.getData());
            return ApiResponseBuilder.buildSuccessResponse(token, "You have been successfully logged in!");
        }
        else {
            ApiError apiError = result.getApiError();
            return ApiResponseBuilder.buildErrorResponse(HttpStatus.BAD_REQUEST, apiError.getErrorMessage(), apiError.getErrorCode(), apiError.getErrorName());
        }
    }
}
