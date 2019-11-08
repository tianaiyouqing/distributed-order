package cloud.tianai.order.business.core.service.impl.search;

import cloud.tianai.order.business.core.OrderBusinessApplicationTests;
import cloud.tianai.order.common.dto.OrderMasterDTO;
import cloud.tianai.order.common.util.gson.GsonUtils;
import cloud.tianai.order.search.form.OrderSearchForm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.List;

public class BusinessOrderPageSearchImplTest extends OrderBusinessApplicationTests {

    @Autowired
    private BusinessOrderPageSearchImpl businessOrderPageSearch;

    @Test
    public void testSearch() {


        OrderSearchForm search = new OrderSearchForm();
//        search.setChannelId("android_001");
        search.setBid("0001");
        List<String> lastNums =
                businessOrderPageSearch.listFutureScrollPageNums(search, null, 1000, 200);


        System.out.println("lastNums =====");
        System.out.println(GsonUtils.gsonString(lastNums));

        String lastFlowNum  = null;
        StopWatch s2 = new StopWatch();
        s2.start();
        List<OrderMasterDTO> result = businessOrderPageSearch.scrollSearch(search, lastFlowNum, 1000);
        s2.stop();

        while (!CollectionUtils.isEmpty(result)) {
            System.out.println("第一个订单号: [ "
                    + result.get(0).getOid()
                    +"], 最后一个订单ID:[ "
                    + result.get(result.size()-1).getOid()
                    + "], 总查询数: " + result.size());
            lastFlowNum = result.get(result.size() - 1).getOid();
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            result = businessOrderPageSearch.scrollSearch(search, lastFlowNum, 1000);
            stopWatch.stop();
            System.out.println("当前查询耗时:" + stopWatch.getTotalTimeMillis() +"ms");
        }
    }

}