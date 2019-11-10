package cloud.tianai.order.core.sdk.dto;

import cloud.tianai.order.core.common.info.OrderAddressInfo;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/9 19:06
 * @Description: 订单优惠需要的参数
 */
@Data
@Builder
public class OrderPromotionParam {

    /** 优惠ID. */
    private String promotionId;

    /** 地址信息. */
    private OrderAddressInfo addressInfo;

    /** 用户信息. */
    private BasicUserInfoDTO userInfoDTO;

    /** 商家信息. */
    private BasicBusinessInfoDTO businessInfoDTO;

    /** 商品信息. */
    private Collection<ProductDTO> productDTOS;
}
