package cloud.tianai.order.core.basic;

import cloud.tianai.order.core.ApplicationTests;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.information.*;
import cloud.tianai.order.core.util.gson.GsonUtils;
import cloud.tianai.order.core.util.id.IdUtils;
import cloud.tianai.order.core.warpper.OrderWrapper;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class OrderSaveServiceTest extends ApplicationTests {

    @Autowired
    private OrderSaveService orderSaveService;

    @Test
    public void insertOrder() {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MultiValueMap<Integer, OrderSaveForm> maps = new LinkedMultiValueMap();
        for (int i = 1; i < 1000000; i++) {
            String uid = IdUtils.getNoRepetitionIdStr();
            String bid = IdUtils.getNoRepetitionIdStr();

            OrderSaveForm orderSaveForm= new OrderSaveForm();
            orderSaveForm.setPayRemark("买家留言了");
            orderSaveForm.setCouponPrice(500L);
            orderSaveForm.setChannelId("android_001");

            BasicUserInfo basicUserInfo = new BasicUserInfo().setUid(uid);

            orderSaveForm.setUserInfo(basicUserInfo);
            AddressInfo addressInfo= new AddressInfo()
                    .setAddressDesc("东六排六号" + i)
                    .setStreet("东关街" + i)
                    .setArea("和顺县" + i)
                    .setCity("晋中市" + i)
                    .setProvince("山西省" + i)
                    .setBuyerName("张三" + i)
                    .setBuyerPhone("13333333333")
                    ;
            Collection<ProductInfo> productInfos = new ArrayList<>(2);
            ProductInfo p1 = new ProductInfo();
            p1.setSpuId(IdUtils.getNoRepetitionIdStr())
                    .setBid(bid)
                    .setProductName("衣服")
                    .setProductPrice(5000L + i + 100)
                    .setProductQuantity(i % 100 + 1)
                    .setProductIcon("http://www.baidu.com")
                    .setSku(IdUtils.getNoRepetitionIdStr())
                    .setSkuDesc("[[aa:bb},{cc:dd}]")
                    .setProductBarcode("0001");

            ProductInfo p2 = new ProductInfo();
            p2.setSpuId(IdUtils.getNoRepetitionIdStr())
                    .setBid(bid)
                    .setProductName("裤子")
                    .setProductPrice(3000L + i + 100)
                    .setProductQuantity(i % 100 + 1)
                    .setProductIcon("http://www.baidu.com")
                    .setSku("12312312313")
                    .setSkuDesc("[[aa:bb},{cc:dd}]")
                    .setProductBarcode("0002");

            ProductInfo p3 = new ProductInfo();
            p3.setSpuId(IdUtils.getNoRepetitionIdStr())
                    .setBid(bid)
                    .setProductName("鞋子")
                    .setProductPrice(6000L)
                    .setProductQuantity(i % 100 + 1)
                    .setProductIcon("http://www.baidu.com")
                    .setSku(IdUtils.getNoRepetitionIdStr())
                    .setSkuDesc("[[aa:bb},{cc:dd}]")
                    .setProductBarcode("0003");
            productInfos.add(p1);
            productInfos.add(p2);
            productInfos.add(p3);

            orderSaveForm.setAddressInfo(addressInfo);

            BasicBusinessInfo businessInfo= new BasicBusinessInfo().setBid(bid);
            orderSaveForm.setBusinessInfo(businessInfo);
            orderSaveForm.setProductInfos(productInfos);

            int i1 = i % 100;
            List<OrderSaveForm> res = maps.computeIfAbsent(i1, k -> new ArrayList<>(255));
            res.add(orderSaveForm);
            StopWatch sw = new StopWatch();
            sw.start();
            orderSaveService.insertOrder(orderSaveForm);
            sw.stop();
            System.out.println("耗时:" + sw.getTotalTimeMillis() +"ms");
        }
// 1118765657732311449882830
//        maps.forEach((k, v) -> {
//            new Thread(() -> {
//                v.forEach(v2 -> {
//                    orderSaveService.insertOrder(v2);
//                });
//            }).start();
//        });

        stopWatch.stop();
        try {
            TimeUnit.DAYS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("耗时:" + stopWatch.getTotalTimeMillis());
    }
}