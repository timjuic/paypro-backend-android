package air.found.payproandroidbackend.core;

public enum ApiError {
    ERR_TERMINAL_ALREADY_EXISTS(1, "ERR_TERMINAL_ALREADY_EXISTS", "Terminal with the same key already exists!"),
    ERR_INVALID_TERMINAL_KEY(2, "ERR_INVALID_TERMINAL_KEY", "Invalid terminal key provided!"),
    ERR_MERCHANT_NOT_FOUND(3, "ERR_MERCHANT_NOT_FOUND", "Merchant with specified ID couldn't be found!"),
    ERR_INVALID_INPUT(7, "ERR_INVALID_INPUT", "Your input is invalid. Please check the entered data for accuracy and ensure it meets the required format or criteria!"),
    ERR_INVALID_MERCHANT_NAME(9, "ERR_INVALID_MERCHANT_NAME", "The merchant name entered is invalid. Please ensure it meets our naming criteria!"),
    ERR_MERCHANT_ALREADY_EXISTS(10, "ERR_MERCHANT_ALREADY_EXISTS", "The merchant with the provided name already exists!"),
    ERR_INVALID_ACCEPTED_CARDS(11, "ERR_INVALID_ACCEPTED_CARDS", "The defined accepted cards are not valid!"),
    ERR_INVALID_STATUS(12, "ERR_INVALID_STATUS", "The defined status is not valid!")
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