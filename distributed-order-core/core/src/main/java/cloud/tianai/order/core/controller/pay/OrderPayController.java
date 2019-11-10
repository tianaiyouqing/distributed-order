package cloud.tianai.order.core.controller.pay;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 20:25
 * @Description: 下单controller
 */
@RestController
@RequestMapping("/order/pay/{env}")
public class OrderPayController {

    @Autowired
    private OrderPayService orderPayService;

    @PostMapping("/create")
    public ApiResponse<OrderCreateResult> createOrder(@RequestBody OrderCreateParam param) {
        // 其实业务层接口在没做分布式之前考虑到接口的健壮性，是做了参数校验的，这里直接把参数传入即可，不需要做参数校验
        ApiResponse<OrderCreateResult> result = orderPayService.createOrder(param);
        return result;
    }
}
