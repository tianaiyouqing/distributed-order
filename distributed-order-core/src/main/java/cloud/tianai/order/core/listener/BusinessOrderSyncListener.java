package cloud.tianai.order.core.listener;

import cloud.tianai.order.core.basic.event.PostOrderInsertEvent;
import cloud.tianai.order.core.business.service.impl.save.BusinessOrderSync;
import cloud.tianai.order.core.warpper.OrderWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 17:08
 * @Description: 监听订单添加实现进行更新
 */
@Slf4j
@Service
public class BusinessOrderSyncListener implements ApplicationListener<PostOrderInsertEvent> {

    @Autowired
    private BusinessOrderSync businessOrderSync;

    @Override
    public void onApplicationEvent(PostOrderInsertEvent event) {
        sync(event);

    }

    @Async
    public void sync(PostOrderInsertEvent event) {
        businessOrderSync.sync(event.getSource());
        log.info("同步数据到商户库集群成功, oid: {}", event.getSource().getOrderMaster().getOid());
    }
}
