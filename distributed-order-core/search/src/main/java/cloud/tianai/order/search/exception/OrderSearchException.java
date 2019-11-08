package cloud.tianai.order.search.exception;

import cloud.tianai.order.common.exception.OrderException;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 16:42
 * @Description: 订单搜索异常s
 */
public class OrderSearchException extends OrderException {

    public OrderSearchException() {
    }

    public OrderSearchException(String message) {
        super(message);
    }

    public OrderSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderSearchException(Throwable cause) {
        super(cause);
    }

    public OrderSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
