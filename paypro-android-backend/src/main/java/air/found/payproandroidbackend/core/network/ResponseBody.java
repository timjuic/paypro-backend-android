package air.found.payproandroidbackend.core.network;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBody<T> {
    private boolean success;
    private String message;
    private Integer errorCode;
    private String errorMessage;
    private T data;

    private ResponseBody(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    private ResponseBody(boolean success, String message, Integer errorCode, String errorMessage) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static <T> ResponseBody<T> success(String message, T data) {
        return new ResponseBody<T>(true, message, null, null).setData(data);
    }

    public static <T> ResponseBody<T> error(String message, Integer errorCode, String errorMessage) {
        return new ResponseBody<T>(false, message, errorCode, errorMessage);
    }

    public T getData() {
        return data;
    }

    public ResponseBody<T> setData(T data) {
        this.data = data;
        return this;
    }
}


