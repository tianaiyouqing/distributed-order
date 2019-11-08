package cloud.tianai.order.business.core.event;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 11:25
 * @Description: 如果是更新操作的事件
 */
public class OrderSyncForUpdateEvent extends OrderSyncEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OrderSyncForUpdateEvent(String source) {
        super(source);
    }
}
