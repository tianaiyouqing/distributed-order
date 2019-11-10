package cloud.tianai.order.core.pay.interceptor;


import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import cloud.tianai.order.core.sdk.dto.BasicBusinessInfoDTO;
import cloud.tianai.order.core.sdk.dto.BasicUserInfoDTO;
import cloud.tianai.order.core.sdk.dto.ProductDTO;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 18:41
 * @Description: 订单创建拦截器
 */
public interface OrderCreateInterceptor {

    ApiResponse<OrderCreateResult> RELEASE = ApiResponse.ofSuccess(OrderCreateResult.EMPTY);
    /**
     * 在下单之前进行拦截
     * @param orderCreateParam 下单时传来的参数
     * @return
     */
    default ApiResponse<OrderCreateResult> beforeCreateOrder(OrderCreateParam orderCreateParam) {
        return RELEASE;
    }

    /**
     * 在执行扣库存之前， 返回状态码为200的直接执行下一个拦截器， 其它状态码直接将数据返回
     * @param productDTOS 商品信息
     * @param userInfoDTO 用户信息
     * @param businessInfoDTO 商户信息
     * @param orderCreateParam 订单创建的参数
     * @return
     */
    default ApiResponse<OrderCreateResult> beforeReduceStock(Collection<ProductDTO> productDTOS,
                                                     BasicUserInfoDTO userInfoDTO,
                                                     BasicBusinessInfoDTO businessInfoDTO,
                                                     OrderCreateParam orderCreateParam) {
        return RELEASE;
    }



}
