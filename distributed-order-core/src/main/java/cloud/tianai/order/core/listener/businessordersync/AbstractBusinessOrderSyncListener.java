package cloud.tianai.order.core.listener.businessordersync;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.basic.event.PostOrderInsertEvent;
import cloud.tianai.order.core.business.service.impl.save.BusinessOrderSync;
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
@Service
public class AbstractBusinessOrderSyncListener {

    @Autowired
    BusinessOrderSync businessOrderSync;
    @Autowired
    BasicOrderService basicOrderService;

    protected void sync(OrderWrapper prderWrapper) {
        businessOrderSync.sync(prderWrapper);
        log.info("同步数据到商户库集群成功, oid: {}", prderWrapper.getOrderMaster().getOid());
    }


    protected void sync(String oid) {
        OrderWrapper orderWrapper = basicOrderService.getOrderDescForOid(oid);
        if(Objects.isNull(orderWrapper)) {
            log.error("【商户订单同步】同步失败， 未找到订单 id:{}", oid);
            return;
        }
        sync(orderWrapper);
    }
}
