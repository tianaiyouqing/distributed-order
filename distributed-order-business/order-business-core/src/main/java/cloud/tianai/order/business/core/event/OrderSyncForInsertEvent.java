package cloud.tianai.order.business.core.event;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 11:23
 * @Description: 订单同步，如果是insert
 */
public class OrderSyncForInsertEvent extends OrderSyncEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public OrderSyncForInsertEvent(String source) {
        super(source);
    }
}
