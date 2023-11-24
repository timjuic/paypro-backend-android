package air.found.payproandroidbackend.core.network;

import lombok.Getter;

@Getter
public class ErrorResponse extends ApiResponse {

    private final int errorCode;
    private final String errorMessage;

    public ErrorResponse(String message, int errorCode, String errorMessage) {
        super(false, message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorResponse(boolean success, String message, int errorCode, String errorMessage) {
        super(success, message);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

