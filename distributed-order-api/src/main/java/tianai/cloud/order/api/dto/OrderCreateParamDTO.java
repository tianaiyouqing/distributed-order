package tianai.cloud.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class OrderCreateParamDTO {

    /** 用户ID. */
    private String uid;

    /** 商品信息. */
    private Collection<SimpleOrderProductDTO> productDTOS;

    /** sku的详细信息， 下单时可不填，订单服务会调用商品服务查询. */
    private Collection<ProductSkuInfoDTO> skuInfoDTOS;
    /** 优惠信息ID. */
    private String couponId;

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

    /** 买家名称. */
    private String buyerName;
    /** 买家手机号. */
    private String buyerPhone;

    /** 省. */
    private String province;
    /** 市. */
    private String city;
    /** 区. */
    private String area;
    /** 街道. */
    private String street;
    /** 地址详情. */
    private String addressDesc;

    /** 自定义优惠价格. */
    private Long customCouponPrice;

    /** 优惠价格. */
    private Long couponPrice;
}
