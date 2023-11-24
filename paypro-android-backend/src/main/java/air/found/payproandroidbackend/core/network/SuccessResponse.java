package air.found.payproandroidbackend.core.network;

public class SuccessResponse extends ApiResponse {

    public SuccessResponse(String message) {
        super(true, message);
    }

    public SuccessResponse(boolean success, String message) {
        super(success, message);
    }
}
