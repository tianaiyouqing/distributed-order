package cloud.tianai.order.core.business.listener.sync;

import cloud.tianai.order.core.exception.OrderSyncException;
import cloud.tianai.order.core.util.canal.CanalDataHolder;
import cloud.tianai.order.core.util.canal.CanalResultData;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 11:55
 * @Description: canal中间件同步抽象类
 */
@Slf4j
public abstract class AbstractCanalBusinessOrderSyncListener extends AbstractBusinessOrderSyncListener {

    /**
     * 消费canal传来的数据
     * @param data canal传来的数据
     * @throws OrderSyncException 同步失败抛出异常
     */
    public void consume(CanalResultData data) throws OrderSyncException {
        if(data.getIsDdl()){
            // 如果是ddl ，则不做任何操作
            return;
        }


        before(data);

        Map<String, String> updateData = data.getData().get(0);
        // 读取对应的oid进行读取
        String oid = updateData.get("oid");
        super.onListener(oid);

        after(data);
    }

    protected void after(CanalResultData data) {
        CanalDataHolder.remove();
    }

    protected void before(CanalResultData data) {
        CanalDataHolder.set(data);
    }
}
