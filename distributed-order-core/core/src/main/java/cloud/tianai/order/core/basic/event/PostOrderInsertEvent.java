package cloud.tianai.order.core.basic.event;

import cloud.tianai.order.core.common.wrapper.OrderWrapper;


/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 22:50
 * @Description: 插入订单前事件
 */
public class PostOrderInsertEvent extends OrderInsertEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PostOrderInsertEvent(OrderWrapper source) {
        super(source);
    }
}
