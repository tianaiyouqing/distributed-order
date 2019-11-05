package cloud.tianai.order.core.basic.form;

import cloud.tianai.order.core.information.AddressInfo;
import cloud.tianai.order.core.information.BasicBusinessInfo;
import cloud.tianai.order.core.information.ProductInfo;
import cloud.tianai.order.core.information.BasicUserInfo;
import lombok.Data;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:56
 * @Description: 订单存储需要的数据
 */
@Data
public class OrderSaveForm {

    /** 买家留言. */
    private String payRemark;
    /** 优惠价格. */
    private Long couponPrice;
    /** 自定义优惠价格. */
    private Long customCouponPrice;
    /** 优惠券的ID. */
    private String couponId;
    /** 平台类型. */
    private Integer platformType;
    /** 渠道ID. */
    private String channelId;
    /** 平台ID. */
    private String platformId;
    /** 第三方订单ID. */
    private String externalOrderId;

    /** 订单类型. */
    private Integer orderType;

    /** 用户信息. */
    private BasicUserInfo userInfo;
    /** 地址信息. */
    private AddressInfo addressInfo;
    /** 商户信息. */
    private BasicBusinessInfo businessInfo;
    /** 商品信息. */
    private Collection<ProductInfo> productInfos;
}
