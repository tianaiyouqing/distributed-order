package cloud.tianai.order.core.basic.form;

import cloud.tianai.order.core.common.info.OrderAddressInfo;
import cloud.tianai.order.core.sdk.dto.BasicBusinessInfoDTO;
import cloud.tianai.order.core.sdk.dto.BasicUserInfoDTO;
import cloud.tianai.order.core.sdk.dto.ProductDTO;
import cloud.tianai.order.core.sdk.dto.PromotionInfoDTO;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:56
 * @Description: 订单存储需要的数据
 */
@Data
public class OrderSaveForm {

    /** 买家留言. */
    private String payRemark;
    /** 渠道ID. */
    private String channelId;
    /** 平台ID. */
    private String platformId;
    /** 平台类型. */
    private Integer platformType;
    /** 第三方订单ID. */
    private String externalOrderId;
    /** 订单类型. */
    private Integer orderType;
    /** 优惠信息. */
    private PromotionInfoDTO promotionInfoDTO;
    /** 自定义优惠价格. */
    private Long customDiscountFee;
    /** 用户信息. */
    private BasicUserInfoDTO userInfo;
    /** 地址信息. */
    private OrderAddressInfo orderAddressInfo;
    /** 商户信息. */
    private BasicBusinessInfoDTO businessInfo;
    /** 商品信息. */
    private Collection<ProductDTO> productDTOS;
    /** 订单创建时间. */
    private Date orderCreateTime;
}
