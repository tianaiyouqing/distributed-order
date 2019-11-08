package cloud.tianai.order.core.pay.test.controller;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.OrderCreateResult;
import cloud.tianai.order.core.common.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.api.basic.enums.OrderTypeEnum;
import cloud.tianai.order.common.util.id.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/test/{env}")
public class TestController {

    @Autowired
    private OrderPayService orderPayService;

    @GetMapping("/createOrder")
    public ApiResponse<OrderCreateResult> testCreateOrder() {
        String uid = IdUtils.getNoRepetitionIdStr();
        OrderCreateParamDTO param = new OrderCreateParamDTO();
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
        param.setCouponId(IdUtils.getNoRepetitionIdStr());
        param.setPayRemark("这是买家留言");
        param.setPlatformType(1);
        param.setChannelId("qd:0001");
        param.setPlatformId("pt:0001");
        param.setOrderType(OrderTypeEnum.CASH_ON_DELIVERY.getCode());
        param.setBuyerName("zhangsan");
        param.setBuyerPhone("133333333333");
        param.setProvince("山西");
        param.setCity("太原市");
        param.setArea("小店区");
        param.setStreet("八一街");
        param.setAddressDesc("xxx小区");

        ApiResponse<OrderCreateResult> response =
                orderPayService.createOrder(param);
       return response;
    }


}
