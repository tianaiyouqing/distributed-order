package cloud.tianai.order.core.pay.test;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import cloud.tianai.order.core.pay.interceptor.OrderCreateInterceptor;
import cloud.tianai.order.core.sdk.dto.BasicBusinessInfoDTO;
import cloud.tianai.order.core.sdk.dto.BasicUserInfoDTO;
import cloud.tianai.order.core.sdk.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/10 14:22
 * @Description: 测试拦截器
 */
@Service
public class TestOrderCreateInterceptor implements OrderCreateInterceptor {

    @Override
    public ApiResponse<OrderCreateResult> beforeCreateOrder(OrderCreateParam orderCreateParam) {
        String uid = orderCreateParam.getUid();
        if("123456".equals(uid)) {
            return ApiResponse.ofCheckError("该用户是黑名单");
        }
        // 否则放行
        return RELEASE;
    }

    @Override
    public ApiResponse<OrderCreateResult> beforeReduceStock(Collection<ProductDTO> productDTOS,
                                                            BasicUserInfoDTO userInfoDTO,
                                                            BasicBusinessInfoDTO businessInfoDTO,
                                                            OrderCreateParam orderCreateParam) {
        for (ProductDTO productDTO : productDTOS) {
            String skuId = productDTO.getSkuId();
            if("123456".equals(skuId)) {
                return ApiResponse.ofCheckError("SKUID 为 123456 的商品禁止下单");
            }
        }
        return RELEASE;
    }
}
