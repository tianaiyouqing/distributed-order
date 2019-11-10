package cloud.tianai.order.core.api.pay.dto;

import cloud.tianai.order.core.common.info.OrderAddressInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 14:18
 * @Description: 订单创建所需参数
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateParam implements Serializable {

    /** 用户ID. */
    private String uid;

    /** 商品信息. */
    private Collection<SimpleOrderProductDTO> productDTOS;

    /** 买家的留言. */
    private String payRemark;

    /** 平台类型. */
    private Integer platformType;
    /** 渠道ID. */
    private String channelId;
    /** 平台ID. */
    private String platformId;

    /** 订单类型. */
    private Integer orderType;

    /** 地址信息. */
    private OrderAddressInfo address;

    /** 优惠ID. */
    private String promotionId;
    /** 自定义优惠价格. */
    private Long customDiscountFee;
}
