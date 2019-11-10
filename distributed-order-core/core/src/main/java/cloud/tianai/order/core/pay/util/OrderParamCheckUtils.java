package cloud.tianai.order.core.pay.util;

import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.util.OrderCheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import cloud.tianai.order.common.response.ApiResponse;

import java.util.Collection;
import java.util.Objects;

public class OrderParamCheckUtils {


    public static ApiResponse checkNecessaryParam(OrderCreateParam paramDTO) {
        String uid = paramDTO.getUid();
        Collection<SimpleOrderProductDTO> productDTOS = paramDTO.getProductDTOS();
        ApiResponse<?> addressCheckResult = OrderCheckUtils.checkAddressInfo(paramDTO.getAddress());
        if(!addressCheckResult.isSuccess()) {
            return addressCheckResult;
        }
        if(StringUtils.isBlank(uid)) {
            return ApiResponse.ofCheckError("用户ID为空");
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
