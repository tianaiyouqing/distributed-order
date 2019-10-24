package cloud.tianai.order.core.configuration.shardingjdbc;

import cloud.tianai.order.core.holder.ApplicationContextHolder;
import cloud.tianai.order.core.id.ShardingIdHolder;
import cloud.tianai.order.core.util.ConsistentHash;
import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.*;

public abstract class AbstractOrderShardingAlgorithm implements ComplexKeysShardingAlgorithm, RangeShardingAlgorithm {
    private Map<String, ConsistentHash> consistentHashMap = new HashMap<>(2);

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
        String logicName = getLogicName(availableTargetNames.iterator().next());
        ConsistentHash consistentHash = getConsistentHash(logicName, availableTargetNames);
        ShardingIdHolder shardingIdHolder = ApplicationContextHolder.getApplicationContext().getBean(ShardingIdHolder.class);
        if(Objects.isNull(shardingIdHolder)) {
            throw new IllegalArgumentException("分库分表策略出错， 未找到 ShardingIdHolder");
        }
        Collection<String> result = internalDoSharding(consistentHash, shardingValues, shardingIdHolder);
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
            consistentHash = new ConsistentHash(availableTargetNames);
            consistentHashMap.put(logicTableName, consistentHash);
        }
        return consistentHash;
    }


    protected abstract Collection<String> internalDoSharding(ConsistentHash consistentHash, Collection<ShardingValue> shardingValues, ShardingIdHolder shardingIdHolder);

}
