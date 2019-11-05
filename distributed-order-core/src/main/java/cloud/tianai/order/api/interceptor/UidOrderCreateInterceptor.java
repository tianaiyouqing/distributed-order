package cloud.tianai.order.api.interceptor;

import cloud.tianai.order.core.interceptor.OrderInterceptorChain;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.OrderCreateResult;
import tianai.cloud.order.api.response.ApiResponse;

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
