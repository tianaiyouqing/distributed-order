package cloud.tianai.order.business.common.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 15:00
 * @Description: 商户订单主信息
 */
@Data
@TableName("business_order_master")
public class BusinessOrderMasterDO {
    /** 订单ID. */
    @TableId
    private String oid;
    /** 用户ID. */
    private String uid;
    /** 商户ID. */
    private String bid;
    /** 渠道ID. */
    private String channelId;
    /** 平台ID. */
    private String platformId;
    /** 第三方订单ID. */
    private String externalOrderId;

    /** 订单状态. */
    private Integer orderStatus;
    /** 售后状态. */
    private Integer afterSalesStatus;
    /** 平台类型. */
    private Integer platformType;
    /** 订单类型 0：货到付款， 1：在线付款. */
    private Integer orderType;
    /** 订单总价. */
    private Long orderAmount;

    // =========== 优惠相关信息 ============

    /** 优惠ID. */
    private String promotionId;
    /** 优惠信息的名称. */
    private String promotionName;
    /** 优惠价格 = 优惠中心的优惠价格 + 自定义优惠价格. */
    private Long discountFee;
    /** 设置自定义优惠价格. */
    private Long customDiscountFee;

    /** 买家名称. */
    private String buyerName;
    /** 买家手机号. */
    private String buyerPhone;
    /** 买家留言. */
    private String payRemark;

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


    /** 付款时间. */
    private Date paymentTime;
    /** 退款时间. */
    private Date refundTime;
    /** 创建时间. */
    private Date createTime;
    /** 最新更新时间. */
    private Date updateTime;
}
