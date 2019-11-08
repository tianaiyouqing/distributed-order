package cloud.tianai.order.core.basic;

import cloud.tianai.order.common.util.gson.GsonUtils;
import cloud.tianai.order.core.OrderCoreApplicationTests;
import cloud.tianai.order.core.api.basic.BasicOrderService;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BasicOrderServiceTest extends OrderCoreApplicationTests {

    @Autowired
    private BasicOrderService basicOrderService;

    @Test
    public void getOrderDescForOid() {
        OrderWrapper res = basicOrderService.getOrderDescForOid("1118765657732311449882830");
        System.out.println(GsonUtils.gsonString(res));
    }
}