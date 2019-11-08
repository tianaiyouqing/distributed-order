package cloud.tianai.order.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 14:16
 * @Description: 下单时基本的商品信息
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SimpleOrderProductDTO  implements Serializable {
    /** 商品ID. */
    private String spuId;
    /** 购买的数量. */
    private Integer num;
    /** skuID. */
    private String skuId;
    /** sku. */
    private String sku;
}
