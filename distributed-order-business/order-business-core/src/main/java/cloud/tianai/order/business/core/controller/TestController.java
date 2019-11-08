package cloud.tianai.order.business.core.controller;

import cloud.tianai.order.common.dto.OrderMasterDTO;
import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.basic.BasicOrderService;
import cloud.tianai.order.core.api.basic.BasicOrderStatusService;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.search.OrderPageSearchService;
import cloud.tianai.order.search.form.OrderSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestController {

    private final OrderPayService orderPayService;
    private final BasicOrderStatusService basicOrderStatusService;
    private final BasicOrderService basicOrderService;
    private final OrderPageSearchService orderPageSearchService;
    @GetMapping("/test")
    public ApiResponse test()  {
        Collection<OrderDetailDO> res = basicOrderService.listOrderDetailForOid("1119134790374712115349010");
        OrderWrapper orderWrapper = basicOrderService.getOrderDescForOid("1119134790374712115349010");
        return ApiResponse.ofSuccess(orderWrapper);
    }
    @GetMapping(value = "/search")
    public ApiResponse search(@RequestParam(value = "lastFlow", required = false) String lastFlow,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        OrderSearchForm from = new OrderSearchForm();
        from.setBid("0001");
        List<OrderMasterDTO> res = orderPageSearchService.scrollSearch(from, lastFlow, pageSize);
        return ApiResponse.ofSuccess(res);
    }
}
