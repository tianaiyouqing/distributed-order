package cloud.tianai.order.core.pay.interceptor;

import cloud.tianai.order.common.interceptor.OrderInterceptorChain;
import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.OrderCreateResult;
import org.springframework.core.annotation.Order;
import cloud.tianai.order.common.response.ApiResponse;

@Order(1)
//@Service
public class UidOrderCreateInterceptor implements OrderCreateInterceptor {

    @Override
    public ApiResponse<OrderCreateResult> intercept(OrderInterceptorChain<OrderCreateParamDTO, OrderCreateResult> chain,
                                                    OrderCreateParamDTO... param) {
        String uid = param[0].getUid();
        if(Integer.valueOf(uid) % 20 == 0) {
            System.out.println("拦截成功..");
            return ApiResponse.ofSuccess(new OrderCreateResult());
        }
        // 否则继续执行
        return chain.intercept(param);
    }
}
