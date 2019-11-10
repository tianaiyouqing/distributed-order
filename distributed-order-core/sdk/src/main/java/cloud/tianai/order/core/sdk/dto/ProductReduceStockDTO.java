package cloud.tianai.order.core.sdk.dto;

import lombok.Data;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/9 19:42
 * @Description: 商品扣库存数据
 */
@Data
public class ProductReduceStockDTO {

    /** 商品ID. */
    private String spuId;
    /** 购买的数量. */
    private Integer num;
    /** skuID. */
    private String skuId;
    /** sku. */
    private String sku;
}
