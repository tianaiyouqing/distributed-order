package cloud.tianai.order.core.exception;

/**
 * @Auther: 天爱有情
 * @Date: 2019/10/24 19:12
 * @Description: 订单报错异常
 */
public class OrderSaveException extends OrderException {

    public OrderSaveException() {
    }

    public OrderSaveException(String message) {
        super(message);
    }

    public OrderSaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderSaveException(Throwable cause) {
        super(cause);
    }

    public OrderSaveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
