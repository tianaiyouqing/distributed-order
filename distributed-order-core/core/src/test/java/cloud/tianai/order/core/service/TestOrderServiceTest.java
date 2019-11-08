package cloud.tianai.order.core.service;


import cloud.tianai.order.core.OrderCoreApplicationTests;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestOrderServiceTest extends OrderCoreApplicationTests {

    @Autowired
    private TestOrderService testOrderService;

    @Test
    public void testAdd() {

        OrderMasterDO master= new OrderMasterDO();
        OrderDetailDO detail= new OrderDetailDO();

        master.setOid("111111");
        master.setUid("111");
        master.setBid("222");
        master.setChannelId("333");
        master.setPlatformId("444");
        master.setExternalOrderId("555");

        master.setOrderStatus(1);
        master.setAfterSalesStatus(2);
        master.setPlatformType(3);

        master.setOrderAmount(666L);
        master.setCouponPrice(6L);
        master.setBuyerName("hi 且");
        master.setBuyerPhone("13333333333");
        master.setPayRemark("哈哈");

        detail.setOrderDetailId("11");
        detail.setOid("111111");
        detail.setSpuId("777");
        detail.setProductName("商品");
        detail.setProductPrice(1L);
        detail.setProductQuantity(2);
        detail.setProductIcon("http://www.baidu.com");
        detail.setSku("skuId");
        detail.setSkuDesc("{aaa:bbb}");
        detail.setProductBarcode("123123");

        testOrderService.add(master, detail);

        System.out.println("asdasdasdqweqweqwe");
    }

}
