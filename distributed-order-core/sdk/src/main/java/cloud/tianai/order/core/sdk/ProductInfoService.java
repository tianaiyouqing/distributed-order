package cloud.tianai.order.core.sdk;

import cloud.tianai.order.core.common.dto.ProductSkuInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 14:43
 * @Description: 查询商品基本信息的接口
 */
public interface ProductInfoService {

    /**
     * 查询商品信息
     * @param spuId spuID
     * @param skuId skuID
     * @param sku sku
     * @return ProductSkuInfoDTO
     */
    ProductSkuInfoDTO getProductSku(String spuId, String skuId, String sku);

    /**
     * 一次查询多个商品信息
     * @param params 参数
     * @return Collection<ProductSkuInfoDTO>
     */
    Collection<ProductSkuInfoDTO> listProductSku(Collection<ProductSkuSearchParam> params);


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ProductSkuSearchParam {
        private String spuId;
        private String skuId;
        private String sku;
    }
}
