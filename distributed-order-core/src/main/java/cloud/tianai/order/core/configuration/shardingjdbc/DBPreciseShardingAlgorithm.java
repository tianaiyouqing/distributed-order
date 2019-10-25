package cloud.tianai.order.core.configuration.shardingjdbc;

import cloud.tianai.order.core.holder.DataSourceDbAndTableHolder;
import cloud.tianai.order.core.id.ShardingIdHolder;
import cloud.tianai.order.core.util.ConsistentHash;
import cloud.tianai.order.core.util.gson.GsonUtils;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 14:47
 * @Description: 分库策略
 */
public class DBPreciseShardingAlgorithm extends AbstractOrderShardingAlgorithm {

    private Boolean addLock = false;

    @Override
    protected Collection<String> internalSharding(ConsistentHash consistentHash,
                                                  Collection<ShardingValue> shardingValues,
                                                  ShardingIdHolder shardingIdHolder) {
        if (!addLock) {
            synchronized (addLock) {
                if (!addLock) {
                    DataSourceDbAndTableHolder.setDbs(consistentHash.getServers());
                    addLock = true;
                }
            }
        }

        Collection<String> result = new ArrayList<>(1);
        for (ShardingValue shardingValue : shardingValues) {
            String columnName = shardingValue.getColumnName();
            if (Objects.equals("uid", columnName)) {
                String uid = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getServerForMod(shardingIdHolder.getShardingIdForUserId(uid));
                result.add(dbName);
            }else if(Objects.equals("oid", columnName)) {
                String oid = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getServerForMod(shardingIdHolder.getUserShardingIdForOrderId(oid));
                result.add(dbName);
            }else if(Objects.equals("order_detail_id", columnName)) {
                String orderDetailId = getSingletonValue(shardingValue).toString();
                String server = consistentHash.getServerForMod(shardingIdHolder.getUserShardingIdForOrderDetailId(orderDetailId));
                result.add(server);
            }
        }
        if(!CollectionUtils.isEmpty(result))  {
            return result;
        }
        throw new IllegalArgumentException("分库策略 未找到 oid 或者 uid");
    }

}
