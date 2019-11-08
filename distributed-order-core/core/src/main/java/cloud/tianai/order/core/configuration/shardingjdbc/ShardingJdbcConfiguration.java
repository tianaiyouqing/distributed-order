package cloud.tianai.order.core.configuration.shardingjdbc;

import cloud.tianai.order.core.configuration.shardingjdbc.business.BusinessDbPreciseShardingAlgorithm;
import cloud.tianai.order.core.configuration.shardingjdbc.business.BusinessTablePreciseShardingAlgorithm;
import cloud.tianai.order.core.configuration.shardingjdbc.user.DBPreciseShardingAlgorithm;
import cloud.tianai.order.core.configuration.shardingjdbc.user.TablePreciseShardingAlgorithm;
import com.alibaba.druid.pool.DruidDataSource;


import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 14:36
 * @Description: 配置分库分表策略
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class ShardingJdbcConfiguration {
    /** db属性配置. */
    private Map<String, DataSourceProperties> db;
    /** shardingJdbc属性配置. */
    private Properties prop;

    @Bean
    public DataSource getShardingDataSource() {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 用户分库分表策略
        addUserShardingRule(shardingRuleConfig);

        // 获取数据源对象
        DataSource dataSource = null;
        try {
            dataSource = ShardingDataSourceFactory.createDataSource(getDatasource(), shardingRuleConfig, new HashMap<>(1), getShardingConfig());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    private void addUserShardingRule(ShardingRuleConfiguration shardingRuleConfig) {
        // 获取 order_master 分库分表策略
        TableRuleConfiguration orderMasterTableRuleConfig = getOrderMasterTableRuleConfiguration();
        // 获取 order_detail 分库分表策略
        TableRuleConfiguration orderDetailTableRuleConfig = getOrderDetailTableRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderMasterTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(orderDetailTableRuleConfig);
    }

    private void addBusinessShardingRule(ShardingRuleConfiguration shardingRuleConfig) {
        // order_master 分库分表策略
        TableRuleConfiguration businessOrderMasterTableRuleConfiguration = getBusinessOrderMasterTableRuleConfiguration();
        // order_detail 分库分表策略
        TableRuleConfiguration businessOrderDetailTableRuleConfiguration = getBusinessOrderDetailTableRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(businessOrderMasterTableRuleConfiguration);
        shardingRuleConfig.getTableRuleConfigs().add(businessOrderDetailTableRuleConfiguration);

    }

    private TableRuleConfiguration getBusinessOrderMasterTableRuleConfiguration() {
        // 配置order_master表规则...
        TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();

        orderItemTableRuleConfig.setLogicTable("business_order_master");
        orderItemTableRuleConfig.setActualDataNodes("business_distributed_order_${1..8}.business_order_master_${0..7}");
        //自定义的分片算法实现 用作分库
        ComplexShardingStrategyConfiguration standardStrategy =
                new ComplexShardingStrategyConfiguration("oid,bid", new BusinessDbPreciseShardingAlgorithm());
        // 使用标准分库， 传入一个字段，通过该字段进行分库
        orderItemTableRuleConfig.setDatabaseShardingStrategyConfig(standardStrategy);
        // 分表使用
        // 配置分表策略
        // 使用多字段分表,多个字段中间中逗号分开
        ComplexShardingStrategyConfiguration complexShardingStrategyConfiguration =
                new ComplexShardingStrategyConfiguration("oid,bid", new BusinessTablePreciseShardingAlgorithm());
        orderItemTableRuleConfig.setTableShardingStrategyConfig(complexShardingStrategyConfiguration);
        return orderItemTableRuleConfig;
    }

    private TableRuleConfiguration getBusinessOrderDetailTableRuleConfiguration() {
        // 配置order_item表规则...
        TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();
        orderItemTableRuleConfig.setLogicTable("business_order_detail");
        orderItemTableRuleConfig.setActualDataNodes("business_distributed_order_${1..8}.business_order_detail_${0..7}");
        //自定义的分片算法实现 用作分库， 目前先设置为一个库
        ComplexShardingStrategyConfiguration standardStrategy =
                new ComplexShardingStrategyConfiguration("order_detail_id,oid", new BusinessDbPreciseShardingAlgorithm());
        // 使用标准分库， 传入一个字段，通过该字段进行分库
        orderItemTableRuleConfig.setDatabaseShardingStrategyConfig(standardStrategy);
        //分表使用
        ComplexShardingStrategyConfiguration complexShardingStrategyConfiguration =
                new ComplexShardingStrategyConfiguration("order_detail_id,oid", new BusinessTablePreciseShardingAlgorithm());
        // 配置分表策略
        // 使用多字段分表， 多个字段中间中逗号分开
        orderItemTableRuleConfig.setTableShardingStrategyConfig(complexShardingStrategyConfiguration);
        return orderItemTableRuleConfig;
    }

    private TableRuleConfiguration getOrderDetailTableRuleConfiguration() {
        // 配置order_item表规则...
        TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();
        orderItemTableRuleConfig.setLogicTable("order_detail");
        orderItemTableRuleConfig.setActualDataNodes("distributed_order_${1..8}.order_detail_${0..7}");
        //自定义的分片算法实现 用作分库， 目前先设置为一个库
        ComplexShardingStrategyConfiguration standardStrategy =
                new ComplexShardingStrategyConfiguration("order_detail_id,oid", new DBPreciseShardingAlgorithm());
        // 使用标准分库， 传入一个字段，通过该字段进行分库
        orderItemTableRuleConfig.setDatabaseShardingStrategyConfig(standardStrategy);
        //分表使用
        ComplexShardingStrategyConfiguration complexShardingStrategyConfiguration =
                new ComplexShardingStrategyConfiguration("order_detail_id,oid", new TablePreciseShardingAlgorithm());
        // 配置分表策略
        // 使用多字段分表， 多个字段中间中逗号分开
        orderItemTableRuleConfig.setTableShardingStrategyConfig(complexShardingStrategyConfiguration);
        return orderItemTableRuleConfig;
    }

    private TableRuleConfiguration getOrderMasterTableRuleConfiguration() {
        // 配置order_master表规则...
        TableRuleConfiguration orderItemTableRuleConfig = new TableRuleConfiguration();

        orderItemTableRuleConfig.setLogicTable("order_master");
        orderItemTableRuleConfig.setActualDataNodes("distributed_order_${1..8}.order_master_${0..7}");
        //自定义的分片算法实现 用作分库
        ComplexShardingStrategyConfiguration standardStrategy =
                new ComplexShardingStrategyConfiguration("oid,uid", new DBPreciseShardingAlgorithm());
        // 使用标准分库， 传入一个字段，通过该字段进行分库
        orderItemTableRuleConfig.setDatabaseShardingStrategyConfig(standardStrategy);
        // 分表使用
        // 配置分表策略
        // 使用多字段分表,多个字段中间中逗号分开
        ComplexShardingStrategyConfiguration complexShardingStrategyConfiguration =
                new ComplexShardingStrategyConfiguration("oid,uid", new TablePreciseShardingAlgorithm());
        orderItemTableRuleConfig.setTableShardingStrategyConfig(complexShardingStrategyConfiguration);
        return orderItemTableRuleConfig;
    }

    /**
     * 获取shardingjdbc 配置
     * @return
     */
    private Properties getShardingConfig() {
        return prop;
    }

    /**
     * 获取所有的datasource
     * @return Map<String, DataSource>
     */
    private Map<String, DataSource> getDatasource() {
        Map<String, DataSource> dataSourceMap = new HashMap<>(db.size());
        db.forEach((key, prop) -> {
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(prop.getDriverClassName());
            dataSource.setUrl(prop.getUrl());
            dataSource.setUsername(prop.getUsername());
            dataSource.setPassword(prop.getPassword());
            dataSourceMap.put(key, dataSource);
        });
        return dataSourceMap;
    }

}
