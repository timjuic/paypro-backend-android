package air.found.payproandroidbackend.core;

public class ServiceResult {
    private final boolean success;
    private final ApiError apiError;

    public ServiceResult(boolean success, ApiError apiError) {
        this.success = success;
        this.apiError = apiError;
    }

    public boolean isSuccess() {
        return success;
    }

    public ApiError getApiError() {
        return apiError;
    }
}
