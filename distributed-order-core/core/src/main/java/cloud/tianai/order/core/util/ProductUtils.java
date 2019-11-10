package cloud.tianai.order.core.util;

import cloud.tianai.order.core.api.pay.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.sdk.dto.ProductDTO;
import cloud.tianai.order.core.sdk.dto.ProductReduceStockDTO;
import cloud.tianai.order.core.sdk.dto.ProductSkuInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/9 19:27
 * @Description: 商品相关工具类
 */
@Slf4j
public class ProductUtils {

    public static Collection<ProductReduceStockDTO> warpProductReduceStock(Collection<SimpleOrderProductDTO> productDTOS) {
        if(CollectionUtils.isEmpty(productDTOS)) {
            return Collections.emptyList();
        }
        Collection<ProductReduceStockDTO> productReduceStockDTOS = new ArrayList<>(productDTOS.size());
        for (SimpleOrderProductDTO productDTO : productDTOS) {
            ProductReduceStockDTO productReduceStockDTO = new ProductReduceStockDTO();
            productReduceStockDTO.setSpuId(productDTO.getSpuId());
            productReduceStockDTO.setSkuId(productDTO.getSkuId());
            productReduceStockDTO.setSku(productDTO.getSku());
            productReduceStockDTO.setNum(productDTO.getNum());
            productReduceStockDTOS.add(productReduceStockDTO);
        }
        return productReduceStockDTOS;
    }



    public static Collection<ProductDTO> warpProductDTO(Collection<ProductSkuInfoDTO> productInfos,
                                                  Collection<SimpleOrderProductDTO> simpleOrderProductDTOS ) {
        if(CollectionUtils.isEmpty(simpleOrderProductDTOS) || CollectionUtils.isEmpty(productInfos)) {
            return Collections.emptyList();
        }
        Collection<ProductDTO> productDTOS = new ArrayList<>(productInfos.size());
        for (ProductSkuInfoDTO productInfo : productInfos) {
            ProductDTO productDTO = warpProductDTO(productInfo);
            SimpleOrderProductDTO simpleOrderProductDTO = findSimpleOrderProduct(productDTO,
                    simpleOrderProductDTOS);
            if(Objects.isNull(simpleOrderProductDTO)){
                log.error("包装ProductDTO出错， 通过对应的skuID，sku，spuID 找到对应是simpleOrderProduct, product={}, simpleOrders={}", productDTO, simpleOrderProductDTOS);
                throw new IllegalArgumentException("包装ProductDTO出错， 通过对应的skuID，sku，spuID 找到对应是simpleOrderProduct");
            }
            productDTO.setProductQuantity(simpleOrderProductDTO.getNum());
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }

    private static SimpleOrderProductDTO findSimpleOrderProduct(ProductDTO productDTO,
                                                                Collection<SimpleOrderProductDTO> simpleOrderProductDTOS) {
        for (SimpleOrderProductDTO simpleOrderProductDTO : simpleOrderProductDTOS) {
            if(simpleOrderProductDTO.getSpuId().equals(productDTO.getSpuId())) {
                if(StringUtils.isNotBlank(productDTO.getSkuId())) {
                    if(productDTO.getSkuId().equals(simpleOrderProductDTO.getSkuId())) {
                        return simpleOrderProductDTO;
                    }
                    if(productDTO.getSku().equals(simpleOrderProductDTO.getSku())) {
                        return simpleOrderProductDTO;
                    }
                }
            }
        }
        return null;
    }

    public static ProductDTO warpProductDTO(ProductSkuInfoDTO productInfo) {
        assert  productInfo != null;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setSpuId(productInfo.getSpuId());
        productDTO.setSkuId(productInfo.getSkuId());
        productDTO.setSku(productInfo.getSku());
        productDTO.setSkuDesc(productInfo.getSkuStr());
        productDTO.setBid(productInfo.getBid());
        productDTO.setProductName(productInfo.getProductName());
        productDTO.setProductPrice(productInfo.getProductPrice());
        productDTO.setProductIcon(productInfo.getProductIcon());
        productDTO.setProductBarcode(productInfo.getBarcode());
        return productDTO;
    }

}
