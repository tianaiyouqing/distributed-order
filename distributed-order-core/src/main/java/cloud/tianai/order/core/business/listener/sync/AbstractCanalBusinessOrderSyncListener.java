package cloud.tianai.order.core.business.listener.sync;

import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.exception.OrderSyncException;
import cloud.tianai.order.core.util.canal.CanalDataHolder;
import cloud.tianai.order.core.util.canal.CanalResultData;
import cloud.tianai.order.core.util.canal.CanalUtils;
import cloud.tianai.order.core.util.canal.MysqlEventType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
     *
     * @param data canal传来的数据
     * @throws OrderSyncException 同步失败抛出异常
     */
    public void consumeForMaster(CanalResultData<OrderMasterDO> data) throws OrderSyncException {
        if (MysqlEventType.INSERT.equals(data.getType())
                || MysqlEventType.UPDATE.equals(data.getType())
                || MysqlEventType.DELETE.equals(data.getType())
        ) {
            before(data);
            String oid = data.getData().getOid();
            // 读取对应的oid进行读取
            super.onListener(oid);
            after(data);
        }
        // 如果是ddl，不做任何操作
    }

    public void consumeForDetail(CanalResultData<OrderDetailDO> data) throws OrderSyncException {
        if (MysqlEventType.INSERT.equals(data.getType())
                || MysqlEventType.UPDATE.equals(data.getType())
                || MysqlEventType.DELETE.equals(data.getType())
        ) {
            before(data);
            String oid = data.getData().getOid();
            // 读取对应的oid进行读取
            super.onListener(oid);
            after(data);
        }
        // 如果是ddl，不做任何操作
    }

    public void consumeForListMap(CanalResultData<List<Map<String, String>>> data) throws OrderSyncException {
        if (MysqlEventType.INSERT.equals(data.getType())
                || MysqlEventType.UPDATE.equals(data.getType())
                || MysqlEventType.DELETE.equals(data.getType())
        ) {
            before(data);
            String oid = data.getData().get(0).get("oid");
            // 读取对应的oid进行读取
            super.onListener(oid);
            after(data);
        }
        // 如果是ddl，不做任何操作
    }

    protected void after(CanalResultData data) {
        CanalDataHolder.remove();
    }

    protected void before(CanalResultData data) {
        CanalDataHolder.set(data);
    }
}
