package cloud.tianai.order.core.pay.test.controller;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import cloud.tianai.order.core.api.pay.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.common.enums.OrderTypeEnum;
import cloud.tianai.order.common.util.id.IdUtils;
import cloud.tianai.order.core.common.info.OrderAddressInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
