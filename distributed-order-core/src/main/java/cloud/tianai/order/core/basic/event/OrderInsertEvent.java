package cloud.tianai.order.core.basic.event;

import cloud.tianai.order.core.warpper.OrderWrapper;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 22:56
 * @Description: 添加订单事件
 */
public class OrderInsertEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OrderInsertEvent(OrderWrapper source) {
        super(source);
    }

    @Override
    public OrderWrapper getSource() {
        return (OrderWrapper) super.getSource();
    }
}
