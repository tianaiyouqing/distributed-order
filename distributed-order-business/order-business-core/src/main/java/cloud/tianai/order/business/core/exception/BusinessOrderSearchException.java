package cloud.tianai.order.business.core.exception;

import cloud.tianai.order.search.exception.OrderSearchException;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 21:51
 * @Description: 商户订单搜索异常
 */
public class BusinessOrderSearchException extends OrderSearchException {
    public BusinessOrderSearchException() {
    }

    public BusinessOrderSearchException(String message) {
        super(message);
    }

    public BusinessOrderSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessOrderSearchException(Throwable cause) {
        super(cause);
    }

    public BusinessOrderSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
