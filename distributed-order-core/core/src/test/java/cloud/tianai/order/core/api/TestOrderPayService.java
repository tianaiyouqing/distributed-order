package cloud.tianai.order.core.api;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.common.util.id.IdUtils;
import cloud.tianai.order.core.OrderCoreApplicationTests;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.OrderCreateResult;
import cloud.tianai.order.core.common.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.api.basic.enums.OrderTypeEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class TestOrderPayService extends OrderCoreApplicationTests {

    @Autowired
    private OrderPayService orderPayService;

    @Test
    public void createOrder() throws InterruptedException {

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
        if(response.isSuccess()) {
            System.out.println("response oid:" + response.getData().getOid());
        }else {
            System.out.println("下单失败 e:" + response.getMsg());
        }
    }


    @Test
    public void createBatchOrder() throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            createOrder();
            stopWatch.stop();
            System.out.println("耗时:" + stopWatch.getTotalTimeMillis() +"ms");
        }

        TimeUnit.DAYS.sleep(1);

    }
}
