package cloud.tianai.order.core.basic;

import cloud.tianai.order.core.ApplicationTests;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.information.*;
import cloud.tianai.order.core.util.gson.GsonUtils;
import cloud.tianai.order.core.util.id.IdUtils;
import cloud.tianai.order.core.warpper.OrderWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class OrderSaveServiceTest extends ApplicationTests {

    @Autowired
    private OrderSaveService orderSaveService;

    @Test
    public void insertOrder() {

        String uid = "156489132156";
        String bid = "156789151277";

        OrderSaveForm orderSaveForm= new OrderSaveForm();
        orderSaveForm.setPayRemark("买家留言了");
        orderSaveForm.setCouponPrice(500L);
        orderSaveForm.setChannelId("android_001");

        BasicUserInfo basicUserInfo = new BasicUserInfo().setUid(uid);

        orderSaveForm.setUserInfo(basicUserInfo);
        AddressInfo addressInfo= new AddressInfo()
                .setAddressDesc("东六排六号")
                .setStreet("东关街")
                .setArea("和顺县")
                .setCity("晋中市")
                .setProvince("山西省")
                .setBuyerName("张三")
                .setBuyerPhone("13333333333")
                ;
        Collection<ProductInfo> productInfos = new ArrayList<>(2);
        ProductInfo p1 = new ProductInfo();
        p1.setPid("1111111")
                .setBid(bid)
                .setProductName("衣服")
                .setProductPrice(5000L)
                .setProductQuantity(2)
                .setProductIcon("http://www.baidu.com")
                .setProductSku("12312312313")
                .setProductSkuDesc("[[aa:bb},{cc:dd}]")
                .setProductBarcode("0001");

        ProductInfo p2 = new ProductInfo();
        p2.setPid("2222222")
                .setBid(bid)
                .setProductName("裤子")
                .setProductPrice(3000L)
                .setProductQuantity(1)
                .setProductIcon("http://www.baidu.com")
                .setProductSku("12312312313")
                .setProductSkuDesc("[[aa:bb},{cc:dd}]")
                .setProductBarcode("0002");

        ProductInfo p3 = new ProductInfo();
        p3.setPid("2222222")
                .setBid(bid)
                .setProductName("鞋子")
                .setProductPrice(6000L)
                .setProductQuantity(3)
                .setProductIcon("http://www.baidu.com")
                .setProductSku("12312312313")
                .setProductSkuDesc("[[aa:bb},{cc:dd}]")
                .setProductBarcode("0003");
        productInfos.add(p1);
        productInfos.add(p2);
        productInfos.add(p3);

        orderSaveForm.setAddressInfo(addressInfo);

        BasicBusinessInfo businessInfo= new BasicBusinessInfo().setBid(bid);
        orderSaveForm.setBusinessInfo(businessInfo);
        orderSaveForm.setProductInfos(productInfos);


        OrderWrapper res = orderSaveService.insertOrder(orderSaveForm);

        System.out.println(GsonUtils.gsonString(res));

        System.out.println("=======");


    }
}