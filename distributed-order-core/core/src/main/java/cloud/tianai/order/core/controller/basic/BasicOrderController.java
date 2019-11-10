package cloud.tianai.order.core.controller.basic;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.common.response.ApiResponseStatusEnum;
import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.common.wrapper.BasicOrderWrapper;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 22:14
 * @Description: 基础订单controller
 */
@RestController
@RequestMapping("/order/basic/{env}")
public class BasicOrderController {

    @Autowired
    private BasicOrderService basicOrderService;

    @GetMapping("/get/desc/{oid}")
    public ApiResponse<BasicOrderWrapper> getOrderDescForOid(@PathVariable("oid") String oid) {
        OrderWrapper orderWrapper = basicOrderService.getOrderDescForOid(oid);
        if(Objects.isNull(orderWrapper)){
            return ApiResponse.ofStatus(ApiResponseStatusEnum.NOT_FOUND);
        }
        // feign不能直接返回接口，和feign同步， 直接返回简单实例的orderWrapper
        BasicOrderWrapper basicOrderWrapper = new BasicOrderWrapper(orderWrapper);
        return ApiResponse.ofSuccess(basicOrderWrapper);
    }
}
