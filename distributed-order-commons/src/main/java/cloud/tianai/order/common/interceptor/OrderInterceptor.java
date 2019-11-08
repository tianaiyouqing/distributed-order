package cloud.tianai.order.common.interceptor;


import cloud.tianai.order.common.response.ApiResponse;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 17:22
 * @Description: 订单创建拦截器
 */
public interface OrderInterceptor<T,R> {

    /**
     * 创建订单前
     * @param chain
     * @return
     */
    ApiResponse<R> intercept(OrderInterceptorChain<T, R> chain, T... param);
}
