package cloud.tianai.order.common.spring.boot;

import cloud.tianai.order.common.holder.ApplicationContextHolder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 16:49
 * @Description: 一些公共的东西
 */
@Configuration
public class DistributedOrderCommonAutoConfiguration {

    /**
     * 注入ApplicationContextHolder
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ApplicationContextHolder.class)
    public ApplicationContextHolder applicationContextHolder() {
        return new ApplicationContextHolder();
    }
}
