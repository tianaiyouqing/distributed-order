package cloud.tianai.order.core.api.feign.pay;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.feign.constant.Version;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 20:31
 * @Description: 下单feign接口
 */
@FeignClient(Version.SERVICE_NAME)
@RequestMapping("/order/pay/v1}")
public interface OrderPayFeign {


    /**
     * 下单
     * @param param 下单所需参数
     * @return ApiResponse<OrderCreateResult>
     */
    @PostMapping("/create")
    ApiResponse<OrderCreateResult> createOrder(@RequestBody OrderCreateParam param);

}
