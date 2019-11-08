package cloud.tianai.order.core.api.feign.search;

import cloud.tianai.order.common.dto.OrderMasterDTO;
import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.feign.constant.Version;
import cloud.tianai.order.search.form.OrderSearchForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 20:53
 * @Description: 订单分页搜索 feign接口
 */
@FeignClient(Version.SERVICE_NAME)
@RequestMapping("/order/search/v1")
public interface OrderPageSearchFeign {

    @PostMapping("/scroll/{lastFlowNum}/{pageSize}")
    ApiResponse<List<OrderMasterDTO>> scrollSearch(@RequestBody OrderSearchForm searchParam,
                                                   @PathVariable("lastFlowNum") String lastFlowNum,
                                                   @PathVariable("pageSize") Integer pageSize);


    @PostMapping("/scroll/nums/{lastFlowNum}/{pageSize}/{readNum}")
    ApiResponse<List<String>> listFutureScrollPageNums(@RequestBody OrderSearchForm orderSearchForm,
                                                       @PathVariable("lastFlowNum") String lastFlowNum,
                                                       @PathVariable("pageSize") Integer pageSize,
                                                       @PathVariable("readNum") Integer readNum);

}
