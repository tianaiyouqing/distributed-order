package cloud.tianai.order.lock.impl;

import cloud.tianai.order.lock.LockTemplate;
import cloud.tianai.order.lock.dto.LockDTO;
import cloud.tianai.order.lock.util.LockUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * Title: RedisLockTemplate
 * Description: redisLockTemplate
 *
 * @author: 天爱有情
 * @date: 2018/12/14 10:30
 **/
@Slf4j
public class RedisLockTemplate implements LockTemplate {
    private static final RedisScript<String> SCRIPT_LOCK = new DefaultRedisScript<>("return redis.call('set',KEYS[1],ARGV[1],'NX','PX',ARGV[2])", String.class);
    private static final RedisScript<String> SCRIPT_UNLOCK = new DefaultRedisScript<>("if redis.call('get',KEYS[1]) == ARGV[1] then return tostring(redis.call('del', KEYS[1])==1) else return 'false' end", String.class);
    private static final String LOCK_SUCCESS = "OK";

    /**
     * 默认过期时间120S
     */
    private static final Long DEFAULT_REDIS_EXPIRE = 120000L;

    /**
     * 默认超时时间 2S
     */
    private static final Long DEFAULT_REDIS_TIMEOUT = 2000L;

    private static final String PROCESS_ID = LockUtils.getLocalMAC() + LockUtils.getJvmPid();

    @Setter
    private StringRedisTemplate redisTemplate;

    private String keyPrefix = "";

    @Override
    public void setKeyPrefix(String prefix) {
        this.keyPrefix = prefix;
    }

    @Override
    public String getKeyPrefix() {
        return keyPrefix;
    }

    @Override
    public LockDTO lock(String key, String value) {
        return lock(key, value, DEFAULT_REDIS_EXPIRE);
    }

    @Override
    public LockDTO lock(String key) {
        return lock(key, getLockValue());
    }

    private String getKey(String key) {
        if (key.startsWith(getKeyPrefix())) {
            return key;
        }
        return getKeyPrefix() + key;
    }

    @Override
    public LockDTO lock(String key, String value, Long timeout) {
        if (StringUtils.isBlank(key)) {
            log.error("【redis锁异常】 加锁失败， key 等于空.");
            return null;
        }
        if (StringUtils.isBlank(value)) {
            log.error("【redis锁异常】 加锁失败， value 等于空.");
            return null;
        }
        if (timeout == null || timeout < 1) {
            log.error("【redis锁异常】 加锁失败， timeout 等于空.");
            return null;
        }
        String newKey = getKey(key);
        Object lockResult = redisTemplate.execute(SCRIPT_LOCK,
                Collections.singletonList(newKey),
                value, String.valueOf(timeout));
        if (LOCK_SUCCESS.equals(lockResult)) {
            return new LockDTO(newKey, value, timeout);
        }
        return null;
    }

    @Override
    public LockDTO sleepLock(String key, String value, Long expire, Long timeout) {
        boolean lock = false;
        long start = System.currentTimeMillis();
        LockDTO lockDTO = null;
        while (System.currentTimeMillis() - start < timeout) {
            if ((lockDTO = lock(key, value, expire)) != null) {
                lock = true;
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
        }
        return lockDTO;
    }

    @Override
    public LockDTO sleepLock(String key, String value) {
        return sleepLock(key, value, DEFAULT_REDIS_EXPIRE, DEFAULT_REDIS_TIMEOUT);
    }

    @Override
    public LockDTO sleepLock(String key) {
        return lock(key, getLockValue());
    }

    private String getLockValue() {
        return PROCESS_ID + Thread.currentThread().getId();
    }

    @Override
    public boolean unLock(String key, String value) {
        if (StringUtils.isBlank(key)) {
            log.error("【redis锁异常】 解锁失败， key 等于空.");
        }
        if (StringUtils.isBlank(value)) {
            log.error("【redis锁异常】 解锁失败， value 等于空.");
        }
        String newKey = getKey(key);
        Object releaseResult = redisTemplate.execute(SCRIPT_UNLOCK,
                Collections.singletonList(newKey), value);
        return Boolean.valueOf(releaseResult.toString());
    }

    @Override
    public boolean unLock(LockDTO lockDTO) {
        if (lockDTO == null) {
            return false;
        }
        return unLock(lockDTO.getLockKey(), lockDTO.getLockValue());
    }
}
