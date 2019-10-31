package cloud.tianai.order.core.business.service.impl.search;

import cloud.tianai.order.core.ApplicationTests;
import cloud.tianai.order.core.dto.OrderMasterDTO;
import cloud.tianai.order.core.search.form.OrderSearchForm;
import cloud.tianai.order.core.util.gson.GsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BusinessOrderPageSearchImplTest extends ApplicationTests {

    @Autowired
    private BusinessOrderPageSearchImpl businessOrderPageSearch;

    @Test
    public void testSearch() {


        OrderSearchForm search = new OrderSearchForm();
        search.setChannelId("android_001");
        search.setBid("1189895382025383938");
        List<String> lastNums =
                businessOrderPageSearch.listFutureScrollPageNums(search, null, 5, 100);


        System.out.println("lastNums =====");
        System.out.println(GsonUtils.gsonString(lastNums));

        String lastFlowNum  = null;

        List<OrderMasterDTO> result = businessOrderPageSearch.scrollSearch(search, lastFlowNum, 5);

        result.stream().map(OrderMasterDTO :: getOid).forEach(System.out :: println);

        while (!CollectionUtils.isEmpty(result)) {
            lastFlowNum = result.get(result.size() - 1).getOid();
            result = businessOrderPageSearch.scrollSearch(search, lastFlowNum, 5);
            System.out.println("====================");
            result.stream().map(OrderMasterDTO :: getOid).forEach(System.out :: println);
        }
    }

}