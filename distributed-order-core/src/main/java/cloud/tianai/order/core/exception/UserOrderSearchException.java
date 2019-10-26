package cloud.tianai.order.core.exception;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 16:42
 * @Description: 用户订单搜索异常
 */
public class UserOrderSearchException extends OrderSearchException {

    public UserOrderSearchException() {
    }

    public UserOrderSearchException(String message) {
        super(message);
    }

    public UserOrderSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserOrderSearchException(Throwable cause) {
        super(cause);
    }

    public UserOrderSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
