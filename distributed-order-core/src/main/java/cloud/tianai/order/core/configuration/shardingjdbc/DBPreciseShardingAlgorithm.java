package cloud.tianai.order.core.configuration.shardingjdbc;

import cloud.tianai.order.core.holder.ApplicationContextHolder;
import cloud.tianai.order.core.holder.DataSourceDbAndTableHolder;
import cloud.tianai.order.core.id.ShardingIdHolder;
import cloud.tianai.order.core.util.ConsistentHash;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @Auther: 天爱有情
 * @Date: 2019/10/24 14:47
 * @Description: 分库策略
 */
public class DBPreciseShardingAlgorithm extends AbstractOrderShardingAlgorithm {

    private Boolean addLock = false;

    @Override
    protected Collection<String> internalDoSharding(ConsistentHash consistentHash,
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
            String logicTableName = shardingValue.getLogicTableName();
            String columnName = shardingValue.getColumnName();
            if (Objects.equals("uid", columnName)) {
                String uid = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getServer(shardingIdHolder.getShardingIdForUserId(uid));
                result.add(dbName);
                return result;
            }else if(Objects.equals("oid", columnName)) {
                String oid = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getServer(shardingIdHolder.getUserShardingIdForOrderId(oid));
                result.add(dbName);
                return result;
            }
        }
        throw new IllegalArgumentException("分库策略 未找到 oid 或者 uid");
    }

}
