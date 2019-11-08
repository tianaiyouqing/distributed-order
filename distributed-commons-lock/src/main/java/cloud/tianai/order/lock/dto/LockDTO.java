package cloud.tianai.order.lock.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Title: LockInfo
 * Description: lockinfo
 *
 * @author: 天爱有情
 * @date: 2018/12/21 11:10
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockDTO implements Serializable {
    /**
     * 锁key
     */
    private String lockKey;
    /**
     * 锁值
     */
    private String lockValue;
    /**
     * 过期时间
     */
    private Long expire;


}
