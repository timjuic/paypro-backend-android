package air.found.payproandroidbackend.core;

public enum ApiError {
    ERR_TERMINAL_ALREADY_EXISTS(1, "ERR_TERMINAL_ALREADY_EXISTS", "Terminal with the same key already exists!"),
    ERR_INVALID_TERMINAL_KEY(2, "ERR_INVALID_TERMINAL_KEY", "Invalid terminal key provided!"),
    ERR_MERCHANT_NOT_FOUND(3, "ERR_MERCHANT_NOT_FOUND", "Merchant with specified ID couldn't be found!"),
    ERR_TERMINAL_NOT_FOUND(4, "ERR_TERMINAL_NOT_FOUND", "Terminal with specified ID couldn't be found!"),
    ERR_EMAIL_ALREADY_IN_USE(5, "ERR_EMAIL_ALREADY_IN_USE", "The email address you entered is already associated with an account!"),
    ERR_INVALID_EMAIL_FORMAT(6, "ERR_INVALID_EMAIL_FORMAT", "The email address you entered is not in a valid format. Please enter a valid email address!"),
    ERR_PASSWORD_TOO_WEAK(7, "ERR_PASSWORD_TOO_WEAK", "Your password does not meet the minimum strength requirements. Please create a stronger password!"),
    ERR_INVALID_INPUT(8, "ERR_INVALID_INPUT", "Your input is invalid. Please check the entered data for accuracy and ensure it meets the required format or criteria!"),
    ERR_PASSWORDS_DONT_MATCH(9, "ERR_PASSWORDS_DONT_MATCH", "The entered passwords do not match. Please ensure that both password fields are identical!"),
    ERR_INVALID_MERCHANT_NAME(11, "ERR_INVALID_MERCHANT_NAME", "The merchant name entered is invalid. Please ensure it meets our naming criteria!"),
    ERR_MERCHANT_ALREADY_EXISTS(12, "ERR_MERCHANT_ALREADY_EXISTS", "The merchant with the provided name already exists!"),
    ERR_INVALID_ACCEPTED_CARDS(13, "ERR_INVALID_ACCEPTED_CARDS", "The defined accepted cards are not valid!"),
    ERR_INVALID_STATUS(14, "ERR_INVALID_STATUS", "The defined status is not valid!"),
    ERR_INVALID_CREDENTIALS(15, "ERR_INVALID_CREDENTIALS", "The provided credentials are invalid!")
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