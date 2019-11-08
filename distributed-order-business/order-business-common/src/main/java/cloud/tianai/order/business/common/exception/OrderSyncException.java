package cloud.tianai.order.business.common.exception;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 21:19
 * @Description: 订单同步异常
 */
public class OrderSyncException extends OrderBusinessException {

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
