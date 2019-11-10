package cloud.tianai.order.core.sdk.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:57
 * @Description: 基本的用户信息
 */
@Data
@Accessors(chain = true)
public class BasicUserInfoDTO {

    /** 用户ID. */
    private String uid;

}
