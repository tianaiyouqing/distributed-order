package cloud.tianai.order.business.core.configuration.shardingjdbc;

import cloud.tianai.order.common.holder.ApplicationContextHolder;
import cloud.tianai.order.common.util.ConsistentHash;
import cloud.tianai.order.id.ShardingIdHolder;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 9:03
 * @Description: 抽象的订单分库分表策略
 */
public abstract class AbstractOrderShardingAlgorithm implements ComplexKeysShardingAlgorithm, RangeShardingAlgorithm {

    /** 存储hash算法器的容器. */
    private Map<String, ConsistentHash> consistentHashMap = new HashMap<>(2);

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
        String logicName = getLogicName(availableTargetNames.iterator().next());
        ConsistentHash consistentHash = getConsistentHash(logicName, availableTargetNames);
        ShardingIdHolder shardingIdHolder = ApplicationContextHolder.getApplicationContext().getBean(ShardingIdHolder.class);
        if(Objects.isNull(shardingIdHolder)) {
            throw new IllegalArgumentException("分库分表策略出错， 未找到 ShardingIdHolder");
        }
        Collection<String> result = internalSharding(consistentHash, shardingValues, shardingIdHolder);
        return result;
    }

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, RangeShardingValue shardingValue) {
        throw new IllegalArgumentException("分表策略， 范围分库，未实现");
    }

    protected Object getSingletonValue(ShardingValue shardingValue) {
        Object result = null;
        if (shardingValue instanceof ListShardingValue) {
             result = ((ListShardingValue) shardingValue).getValues().iterator().next();
        } else if(shardingValue instanceof RangeShardingValue) {
            //
        }else if(shardingValue instanceof PreciseShardingValue) {
            result = ((PreciseShardingValue) shardingValue).getValue();
        }
        return result;
    }

    protected String getLogicName(String name) {
        String sub = name.substring(0, name.lastIndexOf("_"));
        return sub;
    }

    protected ConsistentHash getConsistentHash(String logicTableName, Collection<String> availableTargetNames) {
        ConsistentHash consistentHash = consistentHashMap.get(logicTableName);
        if (Objects.isNull(consistentHash)) {
            consistentHash = new ConsistentHash(availableTargetNames, 30);
            consistentHashMap.put(logicTableName, consistentHash);
        }
        return consistentHash;
    }

    /**
     * 内部执行逻辑_(:з」∠)_方法
     * @param consistentHash hash
     * @param shardingValues 传来的只
     * @param shardingIdHolder 分库分表需要的算法器
     * @return Collection<String>
     */
    protected abstract Collection<String> internalSharding(ConsistentHash consistentHash, Collection<ShardingValue> shardingValues, ShardingIdHolder shardingIdHolder);

}
