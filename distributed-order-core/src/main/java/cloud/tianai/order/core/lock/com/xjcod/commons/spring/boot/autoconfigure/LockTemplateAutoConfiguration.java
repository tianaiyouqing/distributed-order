package cloud.tianai.order.core.lock.com.xjcod.commons.spring.boot.autoconfigure;

import cloud.tianai.order.core.lock.com.xjcod.commons.lock.impl.RedisLockTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Title: LockTemplateAutoConfiguration
 * Description: lockTemplate自动装配
 *
 * @author: 天爱有情
 * @date: 2018/12/14 10:50
 **/
@Configuration
public class LockTemplateAutoConfiguration {


    private static final String REDIS_KEY_PREFIX = "lock:";

    @Bean
    @ConditionalOnClass(RedisTemplate.class)
    @ConditionalOnMissingBean(RedisLockTemplate.class)
    public RedisLockTemplate lockTemplate(StringRedisTemplate redisTemplate) {
        RedisLockTemplate redisLockTemplate = new RedisLockTemplate();
        redisLockTemplate.setRedisTemplate(redisTemplate);
        redisLockTemplate.setKeyPrefix(REDIS_KEY_PREFIX);
        return redisLockTemplate;
    }

}
