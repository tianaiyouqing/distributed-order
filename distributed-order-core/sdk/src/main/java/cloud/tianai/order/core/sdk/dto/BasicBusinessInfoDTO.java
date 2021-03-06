package cloud.tianai.order.core.sdk.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author shaofengl
 * @Author: 天爱有情
 * @Date: 2019/10/24 19:02
 * @Description: 基本商户信息
 */
@Data
@Accessors(chain = true)
public class BasicBusinessInfoDTO {

    private String bid;

}
