package air.found.payproandroidbackend.core;

public enum ApiError {
    ERR_TERMINAL_ALREADY_EXISTS(1, "ERR_TERMINAL_ALREADY_EXISTS", "Terminal with the same key already exists!"),
    ERR_INVALID_TERMINAL_KEY(2, "ERR_INVALID_TERMINAL_KEY", "Invalid terminal key provided!"),
    ERR_MERCHANT_NOT_FOUND(3, "ERR_MERCHANT_NOT_FOUND", "Merchant with specified ID couldn't be found!"),
    ERR_TERMINAL_NOT_FOUND(4, "ERR_TERMINAL_NOT_FOUND", "Terminal with specified ID couldn't be found!")
    ;

    private final int errorCode;
    private final String errorName;
    private final String errorMessage;

    ApiError(int errorCode, String errorName, String errorMessage) {
        this.errorCode = errorCode;
        this.errorName = errorName;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorName() {
        return errorName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}