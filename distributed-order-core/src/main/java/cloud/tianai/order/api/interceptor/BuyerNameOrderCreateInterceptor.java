package cloud.tianai.order.api.interceptor;

import cloud.tianai.order.core.interceptor.OrderInterceptorChain;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.OrderCreateResult;
import tianai.cloud.order.api.response.ApiResponse;

@Order(2)
//@Service
public class BuyerNameOrderCreateInterceptor implements OrderCreateInterceptor {
    @Override
    public ApiResponse<OrderCreateResult> intercept(OrderInterceptorChain<OrderCreateParamDTO, OrderCreateResult> chain,
                                                    OrderCreateParamDTO... param) {
        String buyerName = param[0].getBuyerName();
        if(buyerName.hashCode() % 20 == 0) {
            System.out.println("买家拦截器拦截成功...");
            return ApiResponse.ofSuccess(new OrderCreateResult());
        }
        // 否则直接放行
        return chain.intercept(param);
    }
}
