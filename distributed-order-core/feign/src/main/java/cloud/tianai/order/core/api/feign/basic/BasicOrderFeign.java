package cloud.tianai.order.core.api.feign.basic;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.feign.constant.Version;
import cloud.tianai.order.core.common.wrapper.BasicOrderWrapper;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(Version.SERVICE_NAME)
@RequestMapping("/order/basic/v1")
public interface BasicOrderFeign {

    @GetMapping("/get/desc/{oid}")
    ApiResponse<BasicOrderWrapper> getOrderDescForOid(@PathVariable("oid") String oid);

}
