package cloud.tianai.order.core.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductSkuInfoDTO  implements Serializable {

    /** SPUID. */
    private String spuId;
    /** SKUID. */
    private String skuId;
    /** 商户ID. */
    private String bid;
    /** SKU. */
    private String sku;
    /** sku字符串. */
    private String skuStr;
    /** 商品名称. */
    private String productName;
    /** 商品价格. */
    private Long productPrice;
    /** 条形码. */
    private String barcode;
    /** 商品图片. */
    private String productIcon;
}
