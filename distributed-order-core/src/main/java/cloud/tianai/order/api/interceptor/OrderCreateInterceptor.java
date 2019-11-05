package cloud.tianai.order.api.interceptor;

import cloud.tianai.order.core.interceptor.OrderInterceptor;
import cloud.tianai.order.core.interceptor.OrderInterceptorChain;
import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.OrderCreateResult;
import tianai.cloud.order.api.response.ApiResponse;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 18:41
 * @Description: 订单创建拦截器
 */
public interface OrderCreateInterceptor extends OrderInterceptor<OrderCreateParamDTO, OrderCreateResult> {

}
