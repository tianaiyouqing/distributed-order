package cloud.tianai.order.core.api.feign.basic;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.feign.constant.Version;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(Version.SERVICE_NAME)
@RequestMapping("/order/basic/status/v1")
public interface BasicOrderStatusFeign {

    @PostMapping("/update/{oid}/{orderStatus}")
    ApiResponse<String> updateOrderStatusForOid(@PathVariable("oid") String oid,
                                                @PathVariable("orderStatus") Integer orderStatus);


    @PostMapping("/update/{oid}/{oldOrderStatus}/{orderStatus}")
    ApiResponse<String> updateOrderStatusForOid(@PathVariable("oid") String oid,
                                                @PathVariable("oldOrderStatus") Integer oldOrderStatus,
                                                @PathVariable("orderStatus") Integer orderStatus);


    @PostMapping("/updateForUid/{oid}/{uid}/{orderStatus}")
    ApiResponse<String> updateOrderStatusForOidAndUid(@PathVariable("oid") String oid,
                                                      @PathVariable("uid") String uid,
                                                      @PathVariable("orderStatus") Integer orderStatus);

    @PostMapping("/updateForUid/{oid}/{uid}/{oldOrderStatus}/{orderStatus}")
    ApiResponse<String> updateOrderStatusForOidAndUid(@PathVariable("oid") String oid,
                                                      @PathVariable("uid") String uid,
                                                      @PathVariable("oldOrderStatus") Integer oldOrderStatus,
                                                      @PathVariable("orderStatus") Integer orderStatus);

    @PostMapping("/updateForBid/{oid}/{bid}/{orderStatus}")
    ApiResponse<String> updateOrderStatusForOidAndBid(@PathVariable("oid") String oid,
                                                      @PathVariable("bid") String bid,
                                                      @PathVariable("orderStatus") Integer orderStatus);

    @PostMapping("/updateForBid/{oid}/{bid}/{oldOrderStatus},{orderStatus}")
    ApiResponse<String> updateOrderStatusForOidAndBid(@PathVariable("oid") String oid,
                                                      @PathVariable("bid") String bid,
                                                      @PathVariable("oldOrderStatus") Integer oldOrderStatus,
                                                      @PathVariable("orderStatus") Integer orderStatus);
}
