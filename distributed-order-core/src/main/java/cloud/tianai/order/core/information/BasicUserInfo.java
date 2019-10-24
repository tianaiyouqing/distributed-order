package cloud.tianai.order.core.information;

import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @Auther: 天爱有情
 * @Date: 2019/10/24 18:57
 * @Description: 基本的用户信息
 */
@Data
@Accessors(chain = true)
public class BasicUserInfo {

    /** 用户ID. */
    private String uid;

}
