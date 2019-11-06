package tianai.cloud.order.api;

import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.OrderCreateResult;
import tianai.cloud.order.api.response.ApiResponse;

import java.util.List;

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
    ApiResponse<OrderCreateResult> createOrder(OrderCreateParamDTO paramDTO);


}
