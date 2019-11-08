package cloud.tianai.order.core.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ProductCouponResult  implements Serializable {

    /** 优惠标识ID. */
    private String id;
    /** 优惠券ID. */
    private String couponId;

    /** 每个商品可以优惠的价格. */
    private Map<ProductSkuInfoDTO, Long> couponPrice;

    /** 如果商品的 'couponPrice'为空的话，则使用该字段的优惠价格. */
    private Long otterCouponPrice;
}
