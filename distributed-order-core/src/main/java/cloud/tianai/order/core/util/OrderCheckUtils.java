package cloud.tianai.order.core.util;

import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.information.AddressInfo;
import cloud.tianai.order.core.information.BasicBusinessInfo;
import cloud.tianai.order.core.information.BasicUserInfo;
import cloud.tianai.order.core.information.ProductInfo;
import cloud.tianai.order.core.util.api.response.ApiResponse;
import cloud.tianai.order.core.util.api.response.ApiResponseStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

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
        BasicUserInfo userInfo = orderSaveForm.getUserInfo();
        AddressInfo addressInfo = orderSaveForm.getAddressInfo();
        BasicBusinessInfo businessInfo = orderSaveForm.getBusinessInfo();
        Collection<ProductInfo> productInfos = orderSaveForm.getProductInfos();

        ApiResponse<?> userInfoCheckResult = checkUserInfo(userInfo);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(userInfoCheckResult.getCode())) {
            return userInfoCheckResult;
        }

        ApiResponse<?> addressInfoCheckResult = checkAddressInfo(addressInfo);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(addressInfoCheckResult.getCode())) {
            return addressInfoCheckResult;
        }

      ApiResponse<?> businessInfoCheckResult = checkBusinessInfo(businessInfo);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(businessInfoCheckResult.getCode())) {
            return businessInfoCheckResult;
        }
        ApiResponse<?> productInfosCheckResult = checkProductInfos(productInfos);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(productInfosCheckResult.getCode())) {
            return productInfosCheckResult;
        }

        return ApiResponse.ofSuccess("success");
    }

    private static ApiResponse<?> checkProductInfos(Collection<ProductInfo> productInfos) {
        if(CollectionUtils.isEmpty(productInfos)) {
            return ApiResponse.ofCheckError("商品信息不能为空");
        }
        for (ProductInfo productInfo : productInfos) {
            if(Objects.isNull(productInfo)) {
                return ApiResponse.ofCheckError("商品信息列表中有数据为空");
            }
            String pid = productInfo.getPid();
            String bid = productInfo.getBid();
            String productName = productInfo.getProductName();
            Long productPrice = productInfo.getProductPrice();
            Integer productQuantity = productInfo.getProductQuantity();
            String productIcon = productInfo.getProductIcon();
            String productSku = productInfo.getProductSku();
            if(StringUtils.isBlank(pid)) {
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
            if(StringUtils.isBlank(productSku)) {
                return ApiResponse.ofCheckError("商品SKU不能为空");
            }
        }
        return ApiResponse.ofSuccess("success");
    }

    private static ApiResponse<?> checkBusinessInfo(BasicBusinessInfo businessInfo) {
        if(Objects.isNull(businessInfo)) {
            return ApiResponse.ofCheckError("商户信息为空");
        }
        String bid = businessInfo.getBid();
        if(StringUtils.isBlank(bid)) {
            return ApiResponse.ofCheckError("商户ID不能为空");
        }
        return ApiResponse.ofSuccess("success");
    }

    public static ApiResponse<?> checkAddressInfo(AddressInfo addressInfo) {
        if(Objects.isNull(addressInfo)) {
            return ApiResponse.ofCheckError("地址信息为空");
        }

        String province = addressInfo.getProvince();
        String city = addressInfo.getCity();
        String area = addressInfo.getArea();
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

    public static ApiResponse<?> checkUserInfo(BasicUserInfo userInfo) {
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
