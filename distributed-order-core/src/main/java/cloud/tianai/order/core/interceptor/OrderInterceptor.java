package cloud.tianai.order.core.interceptor;

import com.baomidou.mybatisplus.extension.api.R;
import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.OrderCreateResult;
import tianai.cloud.order.api.response.ApiResponse;

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
    ApiResponse<R> intercept(OrderInterceptorChain<T,R> chain, T... param);
}
