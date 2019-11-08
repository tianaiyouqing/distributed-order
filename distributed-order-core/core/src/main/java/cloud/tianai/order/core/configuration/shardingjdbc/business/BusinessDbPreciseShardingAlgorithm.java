package cloud.tianai.order.core.configuration.shardingjdbc.business;

import cloud.tianai.order.core.configuration.shardingjdbc.AbstractOrderShardingAlgorithm;
import cloud.tianai.order.common.util.ConsistentHash;
import cloud.tianai.order.id.ShardingIdHolder;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 15:26
 * @Description: 商户 订单库集群分库策略
 * 2019年11月7日21:39:45 把商家订单抽离出单独的微服务， 不在主信息做逻辑处理
 */
@Deprecated
public class BusinessDbPreciseShardingAlgorithm extends AbstractOrderShardingAlgorithm {

    @Override
    protected Collection<String> internalSharding(ConsistentHash consistentHash,
                                                  Collection<ShardingValue> shardingValues,
                                                  ShardingIdHolder shardingIdHolder) {

        for (ShardingValue shardingValue : shardingValues) {
            String columnName = shardingValue.getColumnName();
            if(Objects.equals("oid", columnName)) {
                String oid = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getDatabaseForMod(shardingIdHolder.getBusinessShardingIdForOrderId(oid));
                return Collections.singleton(dbName);
            }else if(Objects.equals("bid", columnName)) {
                String bid = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getDatabaseForMod(shardingIdHolder.getShardingIdForBusinessId(bid));
                return Collections.singleton(dbName);
            }else if(Objects.equals("order_detail_id", columnName)) {
                String orderDetailId = getSingletonValue(shardingValue).toString();
                String dbName = consistentHash.getDatabaseForMod(shardingIdHolder.getBusinessShardingIdForOrderDetailId(orderDetailId));
                return Collections.singleton(dbName);
            }
        }
        throw new IllegalArgumentException("分库策略 未找到 oid 或者 uid");
    }
}
