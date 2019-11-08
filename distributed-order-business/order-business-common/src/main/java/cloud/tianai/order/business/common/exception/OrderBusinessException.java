package cloud.tianai.order.business.common.exception;

import cloud.tianai.order.common.exception.OrderException;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 21:18
 * @Description: 商家订单相关异常
 */
public class OrderBusinessException extends OrderException {

    public OrderBusinessException() {
    }

    public OrderBusinessException(String message) {
        super(message);
    }

    public OrderBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderBusinessException(Throwable cause) {
        super(cause);
    }

    public OrderBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
