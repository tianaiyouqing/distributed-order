package cloud.tianai.order.core.configuration.shardingjdbc;

import cloud.tianai.order.core.id.ShardingIdHolder;
import cloud.tianai.order.core.util.ConsistentHash;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;

import java.util.*;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 14:47
 * @Description: 分表策略
 */
public class TablePreciseShardingAlgorithm extends AbstractOrderShardingAlgorithm {

    @Override
    protected Collection<String> internalSharding(ConsistentHash consistentHash,
                                                  Collection<ShardingValue> shardingValues,
                                                  ShardingIdHolder shardingIdHolder) {
        for (ShardingValue shardingValue : shardingValues) {
            String columnName = shardingValue.getColumnName();
            if (Objects.equals("oid", columnName)) {
                String oid = getSingletonValue(shardingValue).toString();
                String server = consistentHash.getServerForMod(shardingIdHolder.getUserShardingIdForOrderId(oid));
                return Collections.singleton(server);
            } else if(Objects.equals("order_detail_id", columnName)) {
                String orderDetailId = getSingletonValue(shardingValue).toString();
                String server = consistentHash.getServerForMod(shardingIdHolder.getUserShardingIdForOrderDetailId(orderDetailId));
                return Collections.singleton(server);
            } else if (Objects.equals("uid", columnName)) {
                String uid = getSingletonValue(shardingValue).toString();
                String server = consistentHash.getServerForMod(shardingIdHolder.getShardingIdForUserId(uid));
                return Collections.singleton(server);
            }
        }

        throw new IllegalArgumentException("分表策略 未找到 oid 或者 uid");
    }
}
