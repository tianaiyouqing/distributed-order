package cloud.tianai.order.core.api.basic.exception;

public class OrderStatusUpdateException extends OrderStatusException {

    public OrderStatusUpdateException() {
    }

    public OrderStatusUpdateException(String message) {
        super(message);
    }

    public OrderStatusUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderStatusUpdateException(Throwable cause) {
        super(cause);
    }

    public OrderStatusUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
