package air.found.payproandroidbackend.core.network;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseBuilder {

    public static <T> ResponseEntity<ResponseBody<T>> buildSuccessResponse(T data, String message) {
        ResponseBody<T> successResponse = ResponseBody.success(message, data);
        return ResponseEntity.ok(successResponse);
    }

    public static <T> ResponseEntity<ResponseBody<T>> buildErrorResponse(HttpStatus status, String message, int errorCode, String errorMessage) {
        ResponseBody<T> errorResponse = ResponseBody.error(message, errorCode, errorMessage);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
