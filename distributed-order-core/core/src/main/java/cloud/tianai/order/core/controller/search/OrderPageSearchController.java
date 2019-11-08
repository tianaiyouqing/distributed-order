package cloud.tianai.order.core.controller.search;

import cloud.tianai.order.common.dto.OrderMasterDTO;
import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.search.OrderPageSearchService;
import cloud.tianai.order.search.form.OrderSearchForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/search/{env}")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderPageSearchController {

    private final OrderPageSearchService pageSearchService;

    @PostMapping("/scroll/{lastFlowNum}/{pageSize}")
    public ApiResponse<List<OrderMasterDTO>> scrollSearch(@RequestBody OrderSearchForm searchParam,
                                                          @PathVariable("lastFlowNum") String lastFlowNum,
                                                          @PathVariable("pageSize") Integer pageSize) {
        List<OrderMasterDTO> searchResult = pageSearchService.scrollSearch(searchParam, lastFlowNum, pageSize);
        return ApiResponse.ofSuccess(searchResult);
    }


    @PostMapping("/scroll/nums/{lastFlowNum}/{pageSize}/{readNum}")
    public ApiResponse<List<String>> listFutureScrollPageNums(@RequestBody OrderSearchForm orderSearchForm,
                                                              @PathVariable("lastFlowNum") String lastFlowNum,
                                                              @PathVariable("pageSize") Integer pageSize,
                                                              @PathVariable("readNum") Integer readNum) {
        List<String> searchResult = pageSearchService.listFutureScrollPageNums(orderSearchForm, lastFlowNum, pageSize, readNum);
        return ApiResponse.ofSuccess(searchResult);
    }
}
