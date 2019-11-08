package cloud.tianai.order.core.api.basic.exception;

import cloud.tianai.order.common.exception.OrderException;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 12:26
 * @Description: 订单状态相关异常
 */
public class OrderStatusException extends OrderException {

    public OrderStatusException() {
    }

    public OrderStatusException(String message) {
        super(message);
    }

    public OrderStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderStatusException(Throwable cause) {
        super(cause);
    }

    public OrderStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
