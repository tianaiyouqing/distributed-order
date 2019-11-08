package cloud.tianai.order.core.pay.interceptor;


import cloud.tianai.order.common.interceptor.OrderInterceptor;
import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.OrderCreateResult;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 18:41
 * @Description: 订单创建拦截器
 */
public interface OrderCreateInterceptor extends OrderInterceptor<OrderCreateParamDTO, OrderCreateResult> {

}
