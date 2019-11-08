package cloud.tianai.order.id.spring.boot;

import cloud.tianai.order.id.DefaultOrderIdGenerator;
import cloud.tianai.order.id.OrderIdGenerator;
import cloud.tianai.order.id.ShardingIdHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderIdAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(OrderIdGenerator.class)
    public DefaultOrderIdGenerator orderIdGenerator() {
        System.out.println("订单ID注入===");
        return new DefaultOrderIdGenerator();
    }
}
