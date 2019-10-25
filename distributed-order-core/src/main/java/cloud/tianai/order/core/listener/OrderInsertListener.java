package cloud.tianai.order.core.listener;

import cloud.tianai.order.core.basic.event.OrderInsertEvent;
import cloud.tianai.order.core.basic.event.PostOrderInsertEvent;
import cloud.tianai.order.core.basic.event.PreOrderInsertEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OrderInsertListener implements ApplicationListener<OrderInsertEvent> {


    @Override
    public void onApplicationEvent(OrderInsertEvent event) {
        if (event instanceof PreOrderInsertEvent) {
            System.out.println("插入订单前 id=" + event.getSource().getOrderMaster().getOid());
        }else if(event instanceof PostOrderInsertEvent) {
            System.out.println("插入订单后 id=" + event.getSource().getOrderMaster().getOid());
        }
    }
}
