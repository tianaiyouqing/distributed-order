package cloud.tianai.order.core.basic.event;

import cloud.tianai.order.core.basic.impl.AbstractOrderStatusService;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 14:02
 * @Description: 订单状态修改后事件
 */
public class PostOrderStatusUpdateEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param updateStatus
     */
    public PostOrderStatusUpdateEvent(AbstractOrderStatusService.OrderStatusUpdate source, boolean updateStatus) {
        super(source);
    }
}
