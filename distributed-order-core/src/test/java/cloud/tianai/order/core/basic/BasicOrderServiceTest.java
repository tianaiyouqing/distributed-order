package cloud.tianai.order.core.basic;

import cloud.tianai.order.core.ApplicationTests;
import cloud.tianai.order.core.util.gson.GsonUtils;
import cloud.tianai.order.core.warpper.OrderWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class BasicOrderServiceTest extends ApplicationTests {

    @Autowired
    private BasicOrderService basicOrderService;

    @Test
    public void getOrderDescForOid() {
        OrderWrapper res = basicOrderService.getOrderDescForOid("1118765657732311449882830");
        System.out.println(GsonUtils.gsonString(res));
    }
}