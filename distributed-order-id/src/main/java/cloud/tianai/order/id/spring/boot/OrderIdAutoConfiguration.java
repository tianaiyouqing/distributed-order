package cloud.tianai.order.id.spring.boot;

import cloud.tianai.order.id.DefaultOrderIdGenerator;
import cloud.tianai.order.id.OrderIdGenerator;
import cloud.tianai.order.id.ShardingIdHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/10 13:22
 * @Description: 订单ID注入
 */
@Configuration
public class OrderIdAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(OrderIdGenerator.class)
    public DefaultOrderIdGenerator orderIdGenerator() {
        return new DefaultOrderIdGenerator();
    }
}
