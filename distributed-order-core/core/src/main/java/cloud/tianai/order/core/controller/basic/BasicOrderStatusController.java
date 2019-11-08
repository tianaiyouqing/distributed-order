package cloud.tianai.order.core.controller.basic;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.common.util.enums.EnumUtils;
import cloud.tianai.order.core.api.basic.OrderStatusService;
import cloud.tianai.order.core.api.basic.enums.OrderStatusEnum;
import cloud.tianai.order.core.api.basic.exception.OrderStatusUpdateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 18:58
 * @Description: 提供给外部调用的基础订单状态修改接口
 */
@RestController
@RequestMapping("/order/basic/status/{env}")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicOrderStatusController {

    private final OrderStatusService orderStatusService;

    @PostMapping("/update/{oid}/{orderStatus}")
    public ApiResponse<String> updateOrderStatusForOid(@PathVariable("oid") String oid,
                                                       @PathVariable("orderStatus") Integer orderStatus) {
        OrderStatusEnum statusEnum = EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
        if(statusEnum == null) {
            return ApiResponse.ofCheckError("订单状态错误");
        }
        try {
            orderStatusService.updateOrderStatusForOid(oid, statusEnum);
        } catch (OrderStatusUpdateException e) {
            return ApiResponse.ofError(e.getMessage());
        }
        return ApiResponse.ofSuccess("更新成功");
    }


    @PostMapping("/update/{oid}/{oldOrderStatus}/{orderStatus}")
    public ApiResponse<String> updateOrderStatusForOid(@PathVariable("oid") String oid,
                                                       @PathVariable("oldOrderStatus") Integer oldOrderStatus,
                                                       @PathVariable("orderStatus") Integer orderStatus) {
        OrderStatusEnum oldStatusEnum = EnumUtils.getByCode(oldOrderStatus, OrderStatusEnum.class);
        OrderStatusEnum statusEnum = EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
        if(statusEnum == null || oldStatusEnum == null) {
            return ApiResponse.ofCheckError("订单状态错误");
        }
        try {
            orderStatusService.updateOrderStatusForOid(oid, oldStatusEnum, statusEnum);
        } catch (OrderStatusUpdateException e) {
            return ApiResponse.ofError(e.getMessage());
        }
        return ApiResponse.ofSuccess("更新成功");
    }

    @PostMapping("/updateForUid/{oid}/{uid}/{orderStatus}")
    public ApiResponse<String> updateOrderStatusForOidAndUid(@PathVariable("oid") String oid,
                                                       @PathVariable("uid") String uid,
                                                       @PathVariable("orderStatus") Integer orderStatus) {
        OrderStatusEnum statusEnum = EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
        if(statusEnum == null ) {
            return ApiResponse.ofCheckError("订单状态错误");
        }
        try {
            orderStatusService.updateOrderStatusForOidAndUid(oid, uid, statusEnum);
        } catch (OrderStatusUpdateException e) {
            return ApiResponse.ofError(e.getMessage());
        }
        return ApiResponse.ofSuccess("更新成功");
    }

    @PostMapping("/updateForUid/{oid}/{uid}/{oldOrderStatus}/{orderStatus}")
    public ApiResponse<String> updateOrderStatusForOidAndUid(@PathVariable("oid") String oid,
                                                             @PathVariable("uid") String uid,
                                                             @PathVariable("oldOrderStatus") Integer oldOrderStatus,
                                                             @PathVariable("orderStatus") Integer orderStatus) {
        OrderStatusEnum statusEnum = EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
        OrderStatusEnum oldStatusEnum = EnumUtils.getByCode(oldOrderStatus, OrderStatusEnum.class);
        if(statusEnum == null || oldStatusEnum == null) {
            return ApiResponse.ofCheckError("订单状态错误");
        }
        try {
            orderStatusService.updateOrderStatusForOidAndUid(oid, uid, oldStatusEnum, statusEnum);
        } catch (OrderStatusUpdateException e) {
            return ApiResponse.ofError(e.getMessage());
        }
        return ApiResponse.ofSuccess("更新成功");
    }


    @PostMapping("/updateForBid/{oid}/{bid}/{orderStatus}")
    public ApiResponse<String> updateOrderStatusForOidAndBid(@PathVariable("oid") String oid,
                                                             @PathVariable("bid") String bid,
                                                             @PathVariable("orderStatus") Integer orderStatus) {
        OrderStatusEnum statusEnum = EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
        if(statusEnum == null) {
            return ApiResponse.ofCheckError("订单状态错误");
        }
        try {
            orderStatusService.updateOrderStatusForOidAndBid(oid, bid, statusEnum);
        } catch (OrderStatusUpdateException e) {
            return ApiResponse.ofError(e.getMessage());
        }
        return ApiResponse.ofSuccess("更新成功");
    }

    @PostMapping("/updateForBid/{oid}/{bid}/{oldOrderStatus},{orderStatus}")
    public ApiResponse<String> updateOrderStatusForOidAndBid(@PathVariable("oid") String oid,
                                                             @PathVariable("bid") String bid,
                                                             @PathVariable("oldOrderStatus") Integer oldOrderStatus,
                                                             @PathVariable("orderStatus") Integer orderStatus) {
        OrderStatusEnum statusEnum = EnumUtils.getByCode(orderStatus, OrderStatusEnum.class);
        OrderStatusEnum oldStatusEnum = EnumUtils.getByCode(oldOrderStatus, OrderStatusEnum.class);
        if(statusEnum == null || oldStatusEnum == null) {
            return ApiResponse.ofCheckError("订单状态错误");
        }
        try {
            orderStatusService.updateOrderStatusForOidAndBid(oid, bid, oldStatusEnum, statusEnum);
        } catch (OrderStatusUpdateException e) {
            return ApiResponse.ofError(e.getMessage());
        }
        return ApiResponse.ofSuccess("更新成功");
    }
}
