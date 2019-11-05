package cloud.tianai.order.api.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.SimpleOrderProductDTO;
import tianai.cloud.order.api.response.ApiResponse;

import java.util.Collection;
import java.util.Objects;

public class OrderParamCheckUtils {


    public static ApiResponse checkNecessaryParam(OrderCreateParamDTO paramDTO) {
        String uid = paramDTO.getUid();
        Collection<SimpleOrderProductDTO> productDTOS = paramDTO.getProductDTOS();
        String buyerName = paramDTO.getBuyerName();
        String buyerPhone = paramDTO.getBuyerPhone();
        String province = paramDTO.getProvince();
        String city = paramDTO.getCity();
        String area = paramDTO.getArea();
        String street = paramDTO.getStreet();

        if(StringUtils.isBlank(uid)) {
            return ApiResponse.ofCheckError("用户ID为空");
        }
        if(StringUtils.isBlank(buyerName)) {
            return ApiResponse.ofCheckError("买家名称为空");
        }
        if(StringUtils.isBlank(buyerPhone)) {
            return ApiResponse.ofCheckError("买家手机为空");
        }
        if(StringUtils.isBlank(province)) {
            return ApiResponse.ofCheckError("省为空");
        }
        if(StringUtils.isBlank(city)) {
            return ApiResponse.ofCheckError("市为空");
        }
        if(StringUtils.isBlank(area)) {
            return ApiResponse.ofCheckError("区为空");
        }
        if(StringUtils.isBlank(street)) {
            return ApiResponse.ofCheckError("街道为空");
        }
        if(CollectionUtils.isEmpty(productDTOS)) {
            return ApiResponse.ofCheckError("商品信息为空");
        }
        for (SimpleOrderProductDTO productDTO : productDTOS) {
            if(Objects.isNull(productDTO)) {
                return ApiResponse.ofCheckError("商品信息部分为空");
            }
            String spuId = productDTO.getSpuId();
            Integer num = productDTO.getNum();
            String skuId = productDTO.getSkuId();
            String sku = productDTO.getSku();
            if(StringUtils.isBlank(spuId)) {
                return ApiResponse.ofCheckError("商品ID为空");
            }
            if(Objects.isNull(num) || num < 1) {
                return ApiResponse.ofCheckError("商品数量不能小于1");
            }
            if(StringUtils.isBlank(skuId) && StringUtils.isBlank(sku)) {
                return ApiResponse.ofCheckError("skuID 和 sku 必须有一样不能为空");
            }
        }
        return ApiResponse.ofSuccess("check success");
    }
}
