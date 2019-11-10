package cloud.tianai.order.core.basic.exception;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 15:50
 * @Description: 订单同步异常
 */
public class OrderSyncException extends OrderSaveException {

    public OrderSyncException() {
    }

    public OrderSyncException(String message) {
        super(message);
    }

    public OrderSyncException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderSyncException(Throwable cause) {
        super(cause);
    }

    public OrderSyncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
