package air.found.payproandroidbackend.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * ServiceResult class to encapsulate the response structure for services.
 *
 * @param <T> The type of data to be returned in the service response.
 */
@Getter
@Builder
@AllArgsConstructor
public class ServiceResult<T> {
    private final boolean success;
    private final ApiError apiError;
    private final T data;


    /**
     * Create a successful ServiceResult with data.
     *
     * @param data The data to be returned.
     * @param <T>  The type of data.
     * @return A successful ServiceResult containing the provided data.
     */
    public static <T> ServiceResult<T> success(T data) {
        return new ServiceResult<>(true, null, data);
    }

    /**
     * Creates a successful ServiceResult without data.
     *
     * @param <T> The type of the data.
     * @return A successful ServiceResult
     */
    public static <T> ServiceResult<T> success() {
        return new ServiceResult<>(true, null, null);
    }

    /**
     * Creates a failed ServiceResult with an ApiError.
     *
     * @param apiError The error information.
     * @param <T>      The type of the data.
     * @return A failed ServiceResult with the specified ApiError.
     */
    public static <T> ServiceResult<T> failure(ApiError apiError) {
        return new ServiceResult<>(false, apiError, null);
    }

    /**
     * Creates a failed ServiceResult with an ApiError and data.
     *
     * @param apiError The error information.
     * @param data     The data to be returned, even in case of failure.
     * @param <T>      The type of the data.
     * @return A failed ServiceResult with the specified ApiError and data.
     */
    public static <T> ServiceResult<T> failure(ApiError apiError, T data) {
        return new ServiceResult<>(false, apiError, data);
    }

    /**
     * Explicitly creates a ServiceResult with the specified success status and data.
     *
     * @param success The success status.
     * @param data    The data to be returned.
     * @param <T>     The type of the data.
     * @return A ServiceResult with the specified success status and data.
     */
    public static <T> ServiceResult<T> explicit(boolean success, T data) {
        return new ServiceResult<>(success, null, data);
    }
}
