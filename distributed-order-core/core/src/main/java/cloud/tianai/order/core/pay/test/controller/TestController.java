package cloud.tianai.order.core.pay.test.controller;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import cloud.tianai.order.core.api.pay.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.common.enums.OrderTypeEnum;
import cloud.tianai.order.common.util.id.IdUtils;
import cloud.tianai.order.core.common.info.OrderAddressInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/test/{env}")
public class TestController {

    @Autowired
    private OrderPayService orderPayService;


    @GetMapping("/ex")
    public ApiResponse exTest() {
        throw new RuntimeException("异常处理");
    }

    @PostMapping("/validate")
    public ApiResponse validate(@Validated @RequestBody Demo demo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ApiResponse.ofCheckError(bindingResult.getFieldError().getDefaultMessage());
        }
        return ApiResponse.ofSuccess("success");
    }

    @Data
    public static class Demo {
        @NotEmpty(message = "name不能为空")
        private String name;
        @NotNull(message = "age不能为空")
        @Min(value = 10, message = "最小能小于10")
        @Max(value = 100, message = "最大不能大于100")
        private Integer age;

    }


    @GetMapping("/createOrder")
    public ApiResponse<OrderCreateResult> testCreateOrder() {
        String uid = IdUtils.getNoRepetitionIdStr();
        OrderCreateParam param = new OrderCreateParam();
        param.setUid(uid);
        Collection<SimpleOrderProductDTO> productDTOS = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SimpleOrderProductDTO s1 = new SimpleOrderProductDTO();
            s1.setSpuId(IdUtils.getNoRepetitionIdStr());
            s1.setSkuId(IdUtils.getNoRepetitionIdStr());
            s1.setNum(2);
            productDTOS.add(s1);
        }
        param.setProductDTOS(productDTOS);
        param.setPlatformId(IdUtils.getNoRepetitionIdStr());
        param.setPayRemark("这是买家留言");
        param.setPlatformType(1);
        param.setChannelId("qd:0001");
        param.setPlatformId("pt:0001");
        param.setOrderType(OrderTypeEnum.CASH_ON_DELIVERY.getCode());
        OrderAddressInfo addressInfo = new OrderAddressInfo();
        addressInfo.setProvince("山西");
        addressInfo.setCity("太原市");
        addressInfo.setArea("小店区");
        addressInfo.setStreet("八一街");
        addressInfo.setAddressDesc("xxx小区");
        addressInfo.setBuyerName("zhangsan");
        addressInfo.setBuyerPhone("13333333333");
        param.setAddress(addressInfo);
        ApiResponse<OrderCreateResult> response =
                orderPayService.createOrder(param);
       return response;
    }

    @GetMapping("/testInterceptor/{uid}/{skuId}")
    public ApiResponse<OrderCreateResult> testInterceptor(@PathVariable("uid") String  uid,
                                                          @PathVariable("skuId") String skuId) {
        OrderCreateParam param = new OrderCreateParam();
        param.setUid(uid);
        Collection<SimpleOrderProductDTO> productDTOS = new ArrayList<>();
        boolean isAddskuID = false;
        for (int i = 0; i < 3; i++) {
            SimpleOrderProductDTO s1 = new SimpleOrderProductDTO();
            if(!isAddskuID) {
                s1.setSkuId(skuId);
                isAddskuID = true;
            }else {
                s1.setSkuId(IdUtils.getNoRepetitionIdStr());
            }
            s1.setSpuId(IdUtils.getNoRepetitionIdStr());
            s1.setNum(2);
            productDTOS.add(s1);
        }
        param.setProductDTOS(productDTOS);
        param.setPlatformId(IdUtils.getNoRepetitionIdStr());
        param.setPayRemark("这是买家留言");
        param.setPlatformType(1);
        param.setChannelId("qd:0001");
        param.setPlatformId("pt:0001");
        param.setOrderType(OrderTypeEnum.CASH_ON_DELIVERY.getCode());
        OrderAddressInfo addressInfo = new OrderAddressInfo();
        addressInfo.setProvince("山西");
        addressInfo.setCity("太原市");
        addressInfo.setArea("小店区");
        addressInfo.setStreet("八一街");
        addressInfo.setAddressDesc("xxx小区");
        addressInfo.setBuyerName("zhangsan");
        addressInfo.setBuyerPhone("13333333333");
        param.setAddress(addressInfo);
        ApiResponse<OrderCreateResult> response =
                orderPayService.createOrder(param);
        return response;
    }


}
