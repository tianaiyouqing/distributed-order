package cloud.tianai.order.business.core.controller;

import cloud.tianai.order.common.dto.OrderMasterDTO;
import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.search.OrderPageSearchService;
import cloud.tianai.order.search.form.OrderSearchForm;
import cloud.tianai.order.search.response.ScrollSearchResponse;
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
    private final OrderPageSearchService orderPageSearchService;
    @GetMapping(value = "/search")
    public ApiResponse search(@RequestParam(value = "lastFlow", required = false) String lastFlow,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        OrderSearchForm from = new OrderSearchForm();
        from.setBid("0001");
        ScrollSearchResponse<OrderMasterDTO> res = orderPageSearchService.scrollSearch(from, lastFlow, pageSize);
        return ApiResponse.ofSuccess(res);
    }
}
