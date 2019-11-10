package cloud.tianai.order.core.util;

import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.common.info.OrderAddressInfo;
import cloud.tianai.order.core.sdk.dto.BasicBusinessInfoDTO;
import cloud.tianai.order.core.sdk.dto.BasicUserInfoDTO;
import cloud.tianai.order.core.sdk.dto.ProductDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.common.response.ApiResponseStatusEnum;

import java.util.Collection;
import java.util.Objects;

public class OrderCheckUtils {




    /**
     * 校验 OrderSaveForm
     * @param orderSaveForm orderSaveForm
     * @return ApiResponse
     */
    public static ApiResponse<?> checkOrderSaveForm(OrderSaveForm orderSaveForm) {
        if(Objects.isNull(orderSaveForm)) {
            return ApiResponse.ofCheckError("orderSaveForm为空");
        }
        BasicUserInfoDTO userInfo = orderSaveForm.getUserInfo();
        OrderAddressInfo orderAddressInfo = orderSaveForm.getOrderAddressInfo();
        BasicBusinessInfoDTO businessInfo = orderSaveForm.getBusinessInfo();
        Collection<ProductDTO> productDTOS = orderSaveForm.getProductDTOS();

        ApiResponse<?> userInfoCheckResult = checkUserInfo(userInfo);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(userInfoCheckResult.getCode())) {
            return userInfoCheckResult;
        }

        ApiResponse<?> addressInfoCheckResult = checkAddressInfo(orderAddressInfo);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(addressInfoCheckResult.getCode())) {
            return addressInfoCheckResult;
        }

      ApiResponse<?> businessInfoCheckResult = checkBusinessInfo(businessInfo);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(businessInfoCheckResult.getCode())) {
            return businessInfoCheckResult;
        }
        ApiResponse<?> productInfosCheckResult = checkProductInfos(productDTOS);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(productInfosCheckResult.getCode())) {
            return productInfosCheckResult;
        }

        return ApiResponse.ofSuccess("success");
    }

    private static ApiResponse<?> checkProductInfos(Collection<ProductDTO> productDTOS) {
        if(CollectionUtils.isEmpty(productDTOS)) {
            return ApiResponse.ofCheckError("商品信息不能为空");
        }
        for (ProductDTO productDTO : productDTOS) {
            if(Objects.isNull(productDTO)) {
                return ApiResponse.ofCheckError("商品信息列表中有数据为空");
            }
            String spuId = productDTO.getSpuId();
            String bid = productDTO.getBid();
            String productName = productDTO.getProductName();
            Long productPrice = productDTO.getProductPrice();
            Integer productQuantity = productDTO.getProductQuantity();
            String productIcon = productDTO.getProductIcon();
            String sku = productDTO.getSku();
            String skuId = productDTO.getSkuId();
            if(StringUtils.isBlank(spuId)) {
                return ApiResponse.ofCheckError("商品ID不能为空");
            }
            if(StringUtils.isBlank(bid)) {
                return ApiResponse.ofCheckError("商品中的商户ID不能为空");
            }
            if(StringUtils.isBlank(productName)) {
                return ApiResponse.ofCheckError("商品名称不能为空");
            }
            if(Objects.isNull(productPrice) || productPrice < 1) {
                return ApiResponse.ofCheckError("商品价格格式错误");
            }
            if(Objects.isNull(productQuantity) || productQuantity < 1) {
                return ApiResponse.ofCheckError("商品数量格式错误");
            }
            if(StringUtils.isBlank(productIcon)) {
                return ApiResponse.ofCheckError("商品图片不能为空");
            }
            if(StringUtils.isBlank(skuId) && StringUtils.isBlank(sku)) {
                return ApiResponse.ofCheckError("商品SKU不能为空");
            }
        }
        return ApiResponse.ofSuccess("success");
    }

    private static ApiResponse<?> checkBusinessInfo(BasicBusinessInfoDTO businessInfo) {
        if(Objects.isNull(businessInfo)) {
            return ApiResponse.ofCheckError("商户信息为空");
        }
        String bid = businessInfo.getBid();
        if(StringUtils.isBlank(bid)) {
            return ApiResponse.ofCheckError("商户ID不能为空");
        }
        return ApiResponse.ofSuccess("success");
    }

    public static ApiResponse<?> checkAddressInfo(OrderAddressInfo orderAddressInfo) {
        if(Objects.isNull(orderAddressInfo)) {
            return ApiResponse.ofCheckError("地址信息为空");
        }

        String province = orderAddressInfo.getProvince();
        String city = orderAddressInfo.getCity();
        String area = orderAddressInfo.getArea();
        String buyerName = orderAddressInfo.getBuyerName();
        String buyerPhone = orderAddressInfo.getBuyerPhone();

        if(StringUtils.isBlank(buyerName)) {
            return ApiResponse.ofCheckError("买家名称为空");
        }
        if(StringUtils.isBlank(buyerPhone)) {
            return ApiResponse.ofCheckError("买家手机为空");
        }
        if(StringUtils.isBlank(province)) {
            return ApiResponse.ofCheckError("地址信息中 省不能为空");
        }
        if(StringUtils.isBlank(city)) {
            return ApiResponse.ofCheckError("地址信息中 市不能为空");
        }
        if(StringUtils.isBlank(area)) {
            return ApiResponse.ofCheckError("地址信息中 区不能为空");
        }
        return ApiResponse.ofSuccess("success");

    }

    public static ApiResponse<?> checkUserInfo(BasicUserInfoDTO userInfo) {
        if(Objects.isNull(userInfo)) {
            return ApiResponse.ofCheckError("用户信息不能为空");
        }
        String uid = userInfo.getUid();
        if(StringUtils.isBlank(uid)) {
            return ApiResponse.ofCheckError("用户信息中 用户ID不能为空");
        }
        return ApiResponse.ofSuccess("success");
    }
}
