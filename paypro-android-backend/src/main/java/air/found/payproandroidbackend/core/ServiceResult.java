package air.found.payproandroidbackend.core;

public class ServiceResult<T> {
    private final boolean success;
    private final ApiError apiError;
    private final T data;

    private ServiceResult(boolean success, ApiError apiError, T data) {
        this.success = success;
        this.apiError = apiError;
        this.data = data;
    }

    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(true, null, data);
    }

    public static <T> ServiceResult<T> success() {
        return new ServiceResult<>(true, null, null);
    }

    public static <T> ServiceResult<T> failure(ApiError apiError) {
        return new ServiceResult<>(false, apiError, null);
    }

    public static <T> ServiceResult<T> failure(ApiError apiError, T data) {
        return new ServiceResult<>(false, apiError, data);
    }

    public static <T> ServiceResult<T> explicit(boolean success, T data) {
        return new ServiceResult<>(success, null, data);
    }

    public boolean isSuccess() {
        return success;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public T getData() {
        return data;
    }
}
