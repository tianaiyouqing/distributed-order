package cloud.tianai.order.core.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 14:11
 * @Description: 订单创建完成后返回的数据
 */
@Data
public class OrderCreateResult implements Serializable {

    /** 订单ID. */
    private String oid;
    /** 用户ID. */
    private String uid;
    /** 商户ID. */
    private String bid;
    /** 创建时间. */
    private Date createTime;
    /** 订单总价. */
    private Long orderAmount;
    /** 优惠价格. */
    private Long couponPrice;
    /** 商品信息. */
    private Collection<SimpleOrderProductDTO> productDTOS;
}
