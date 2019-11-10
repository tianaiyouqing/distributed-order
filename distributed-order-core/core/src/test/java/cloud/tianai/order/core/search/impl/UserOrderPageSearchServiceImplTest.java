package cloud.tianai.order.core.search.impl;

import cloud.tianai.order.common.dto.OrderMasterDTO;
import cloud.tianai.order.common.util.gson.GsonUtils;
import cloud.tianai.order.core.OrderCoreApplicationTests;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import cloud.tianai.order.search.form.OrderSearchForm;
import cloud.tianai.order.search.response.ScrollSearchResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserOrderPageSearchServiceImplTest extends OrderCoreApplicationTests {


    @Autowired
    private UserOrderPageSearchServiceImpl userOrderPageSearchService;


    @Test
    public void test1() {
//        List<OrderMasterDO> result = orderMasterMapper.findtestSearch();
//        System.out.println(result);
    }

    @Test
    public void flowSearchTest() {
        Integer pageSize = 20;
        OrderSearchForm search = new OrderSearchForm();
        search.setUid("1187398437650337793");
        String lastFlowNum = "1118739845138668339493940";
        List<String> lastFlowNums = userOrderPageSearchService.listFutureScrollPageNums(search, lastFlowNum, pageSize, 10);
        System.out.println(GsonUtils.gsonString(lastFlowNums));

        List<OrderMasterDTO> resultData =  new ArrayList<>(255);
        ScrollSearchResponse<OrderMasterDTO> result = userOrderPageSearchService.scrollSearch(search, lastFlowNum, pageSize);
        resultData.addAll(result.getData());
        for (String flowNum : lastFlowNums) {
            lastFlowNum = flowNum;
            result = userOrderPageSearchService.scrollSearch(search, lastFlowNum, pageSize);
            resultData.addAll(result.getData());
        }

        for (int i = 0; i < resultData.size(); i++) {
            System.out.println("index: " + (i+1) +", oid:" + resultData.get(i).getOid());
        }
        System.out.println("==========================");
        System.out.println(GsonUtils.gsonString(resultData));
    }

}