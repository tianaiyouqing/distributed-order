package cloud.tianai.order.common.interceptor;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.common.response.ApiResponseStatusEnum;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 17:56
 * @Description: 订单的拦截器
 */
public class OrderInterceptorChain<T, R> {

    public static ApiResponse NOT_INTERCEPTOR = ApiResponse.ofStatus(ApiResponseStatusEnum.NOT_FOUND);

    private List<OrderInterceptor> interceptors = new ArrayList<>(10);
    private Integer currentInterceptorIndex = -1;
    /** 未被拦截时返回的数据. */

    public OrderInterceptorChain(List<OrderInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public OrderInterceptorChain() {
    }


    public void addInterceptor(OrderInterceptor interceptor) {
        interceptors.add(interceptor);
    }
    public ApiResponse<R> intercept(T... param) {
        if(interceptors == null || interceptors.size() < 1) {
            // 如果没有拦截器，直接放行
            return NOT_INTERCEPTOR;
        }
        if(this.currentInterceptorIndex == this.interceptors.size() - 1) {
            return NOT_INTERCEPTOR;
        }
        OrderInterceptor interceptor = this.interceptors.get(++this.currentInterceptorIndex);
        return interceptor.intercept(this, param);
    }


}
