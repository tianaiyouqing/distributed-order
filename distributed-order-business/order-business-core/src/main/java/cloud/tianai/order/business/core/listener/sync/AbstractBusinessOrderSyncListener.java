package cloud.tianai.order.business.core.listener.sync;

import cloud.tianai.order.business.common.exception.OrderSyncException;
import cloud.tianai.order.business.api.sync.BusinessOrderSync;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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
        if(StringUtils.isBlank(oid)) {
            log.warn("同步订单警告，oid为空");
            return;
        }
        businessOrderSync.sync(oid);
        log.info("同步数据到商户库集群成功, oid: {}", oid);
    }

}
