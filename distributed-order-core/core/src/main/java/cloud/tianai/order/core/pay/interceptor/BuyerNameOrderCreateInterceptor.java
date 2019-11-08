package cloud.tianai.order.core.pay.interceptor;

import cloud.tianai.order.common.interceptor.OrderInterceptorChain;
import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.OrderCreateResult;
import org.springframework.core.annotation.Order;
import cloud.tianai.order.common.response.ApiResponse;

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
