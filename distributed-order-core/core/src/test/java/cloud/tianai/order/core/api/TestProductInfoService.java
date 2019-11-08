package cloud.tianai.order.core.api;

import cloud.tianai.order.common.util.gson.GsonUtils;
import cloud.tianai.order.core.common.dto.ProductSkuInfoDTO;
import cloud.tianai.order.core.sdk.ProductInfoService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//@Service
public class TestProductInfoService implements ProductInfoService {
    @Override
    public ProductSkuInfoDTO getProductSku(String spuId, String skuId, String sku) {
        return null;
    }

    @Override
    public Collection<ProductSkuInfoDTO> listProductSku(Collection<ProductSkuSearchParam> params) {
        Collection<ProductSkuInfoDTO> result = new ArrayList<>(params.size());
        for (ProductSkuSearchParam param : params) {
            ProductSkuInfoDTO productSkuInfoDTO = new ProductSkuInfoDTO();
            productSkuInfoDTO.setSpuId(param.getSpuId());
            productSkuInfoDTO.setSkuId(param.getSkuId());
            productSkuInfoDTO.setSku(param.getSku());

            productSkuInfoDTO.setBid("0001");
            Map<String, String> skuDesc = new HashMap<>(10);
            skuDesc.put("颜色", "蓝色");
            skuDesc.put("尺码", "xxl");
            skuDesc.put("大小", "12");
            productSkuInfoDTO.setSkuStr(GsonUtils.gsonString(skuDesc));

            productSkuInfoDTO.setSku("1:11;2:12;3:13");
            productSkuInfoDTO.setProductName("衣服");
            productSkuInfoDTO.setProductPrice(6000L);
            productSkuInfoDTO.setBarcode("xx001");
            productSkuInfoDTO.setProductIcon("http://pic27.nipic.com/20130313/9252150_092049419327_2.jpg");
            result.add(productSkuInfoDTO);
        }
        return result;
    }
}
