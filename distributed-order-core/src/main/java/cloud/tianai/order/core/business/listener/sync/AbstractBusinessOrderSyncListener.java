package cloud.tianai.order.core.business.listener.sync;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.basic.event.PostOrderInsertEvent;
import cloud.tianai.order.core.business.service.impl.save.BusinessOrderSync;
import cloud.tianai.order.core.exception.OrderSyncException;
import cloud.tianai.order.core.warpper.OrderWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 17:08
 * @Description: 监听订单添加实现进行更新
 */
@Slf4j
public abstract class AbstractBusinessOrderSyncListener {

    @Autowired
    BusinessOrderSync businessOrderSync;


    protected void onListener(String oid) throws OrderSyncException {
        businessOrderSync.sync(oid);
        log.info("同步数据到商户库集群成功, oid: {}", oid);
    }

}
