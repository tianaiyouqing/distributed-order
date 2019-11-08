package cloud.tianai.order.core.api.basic.information;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 19:03
 * @Description: 商品信息
 */
@Data
@Accessors(chain = true)
public class ProductInfo {
    /** 商品ID. */
    private String spuId;
    /** skuID. */
    private String skuId;
    /** sku. */
    private String sku;
    /** sku描述. */
    private String skuDesc;
    /** 商户ID. */
    private String bid;
    /** 商品名称. */
    private String productName;
    /** 商品价格. */
    private Long productPrice;
    /** 商品数量. */
    private Integer productQuantity;
    /** 商品图标. */
    private String productIcon;
    /** 商品商品条形码. */
    private String productBarcode;
}
