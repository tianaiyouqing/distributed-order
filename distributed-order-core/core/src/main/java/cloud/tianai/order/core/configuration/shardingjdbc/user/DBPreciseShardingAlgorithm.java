package cloud.tianai.order.core.configuration.shardingjdbc.user;

import cloud.tianai.order.core.configuration.shardingjdbc.AbstractOrderShardingAlgorithm;
import cloud.tianai.order.core.holder.DataSourceDbAndTableHolder;
import cloud.tianai.order.common.util.ConsistentHash;
import cloud.tianai.order.id.ShardingIdHolder;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
                String dbName = consistentHash.getDatabaseForMod(shardingIdHolder.getShardingIdForUserId(uid));
                return Collections.singleton(dbName);
            }else if(Objects.equals("oid", columnName)) {
                String oid = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getDatabaseForMod(shardingIdHolder.getUserShardingIdForOrderId(oid));
                return Collections.singleton(dbName);
            }else if(Objects.equals("order_detail_id", columnName)) {
                String orderDetailId = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getDatabaseForMod(shardingIdHolder.getUserShardingIdForOrderDetailId(orderDetailId));
                return Collections.singleton(dbName);
            }
        }
        throw new IllegalArgumentException("分库策略 未找到 oid 或者 uid");
    }

}
