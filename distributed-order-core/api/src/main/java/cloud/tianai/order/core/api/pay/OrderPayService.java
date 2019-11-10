package cloud.tianai.order.core.api.pay;


import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 14:34
 * @Description: 订单业务接口
 */
public interface OrderPayService {

    /**
     * 创建订单
     * @param paramDTO 创建订单所需参数
     * @return ApiResponse<List<OrderCreateResult>>
     */
    ApiResponse<OrderCreateResult> createOrder(OrderCreateParam paramDTO);

}
