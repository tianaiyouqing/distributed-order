package cloud.tianai.order.lock;


import cloud.tianai.order.lock.dto.LockDTO;

/**
 * 锁
 *
 * @author 天爱有情
 * @create 2018-12-14 9:54
 **/
public interface LockTemplate {

    /**
     * 设置key的前缀
     *
     * @param prefix
     */
    void setKeyPrefix(String prefix);

    /**
     * 获取key的前缀， 没有默认是""
     */
    String getKeyPrefix();

    /**
     * 加锁
     * (解锁失败，返回null)
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    LockDTO lock(String key, String value);

    /**
     * 加锁 key
     * (解锁失败，返回null)
     *
     * @param key
     * @return key
     */
    LockDTO lock(String key);

    /**
     * 加锁
     *
     * @param key     key
     * @param value   value
     * @param timeout 超时
     * @return boolean
     */
    LockDTO lock(String key, String value, Long timeout);

    /**
     * 超时锁
     * (解锁失败，返回null)
     *
     * @param key     key
     * @param value   value
     * @param expire  过期时间
     * @param timeout 超时
     * @return boolean
     */
    LockDTO sleepLock(String key, String value, Long expire, Long timeout);

    /**
     * 超时锁
     * (解锁失败，返回null)
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    LockDTO sleepLock(String key, String value);

    /**
     * 加锁
     * (解锁失败，返回null)
     *
     * @param key key
     * @return boolean
     */
    LockDTO sleepLock(String key);

    /**
     * 解锁
     * (解锁失败 返回false)
     *
     * @param key   key
     * @param value value
     */
    boolean unLock(String key, String value);

    /**
     * 解锁
     * (解锁失败，返回false)
     *
     * @param lockDTO
     */
    boolean unLock(LockDTO lockDTO);
}
