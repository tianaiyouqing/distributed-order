package cloud.tianai.order.api;

import cloud.tianai.order.core.ApplicationTests;
import cloud.tianai.order.core.enums.OrderTypeEnum;
import cloud.tianai.order.core.util.id.IdUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import tianai.cloud.order.api.OrderPayService;
import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.OrderCreateResult;
import tianai.cloud.order.api.dto.SimpleOrderProductDTO;
import tianai.cloud.order.api.response.ApiResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class TestOrderPayService extends ApplicationTests {

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
