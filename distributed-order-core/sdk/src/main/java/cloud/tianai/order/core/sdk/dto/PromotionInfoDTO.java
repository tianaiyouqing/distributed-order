package cloud.tianai.order.core.sdk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/9 18:13
 * @Description: 促销信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionInfoDTO {

    /** ID. */
    private String id;

    /** 优惠ID. */
    private String promotionId;

    /** 促销名称. */
    private String promotionName;

    /** 优惠价格. */
    private Long discountFee;
}
