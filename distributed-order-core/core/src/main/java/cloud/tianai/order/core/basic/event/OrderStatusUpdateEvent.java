package cloud.tianai.order.core.basic.event;

import cloud.tianai.order.core.basic.impl.AbstractBasicOrderStatusService;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 14:02
 * @Description: 订单状态修改事件
 */
public class OrderStatusUpdateEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OrderStatusUpdateEvent(AbstractBasicOrderStatusService.OrderStatusUpdate source) {
        super(source);
    }

    @Override
    public AbstractBasicOrderStatusService.OrderStatusUpdate getSource() {
        return (AbstractBasicOrderStatusService.OrderStatusUpdate) super.getSource();
    }
}
