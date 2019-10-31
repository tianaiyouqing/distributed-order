package cloud.tianai.order.core.search.impl;

import cloud.tianai.order.core.ApplicationTests;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.dto.OrderMasterDTO;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import cloud.tianai.order.core.search.form.OrderSearchForm;
import cloud.tianai.order.core.util.gson.GsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserOrderPageSearchServiceImplTest extends ApplicationTests {


    @Autowired
    private UserOrderPageSearchServiceImpl userOrderPageSearchService;
    @Autowired
    private OrderMasterMapper orderMasterMapper;


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
        List<OrderMasterDTO> result = userOrderPageSearchService.scrollSearch(search, lastFlowNum, pageSize);
        resultData.addAll(result);
        for (String flowNum : lastFlowNums) {
            lastFlowNum = flowNum;
            result = userOrderPageSearchService.scrollSearch(search, lastFlowNum, pageSize);
            resultData.addAll(result);
        }

        for (int i = 0; i < resultData.size(); i++) {
            System.out.println("index: " + (i+1) +", oid:" + resultData.get(i).getOid());
        }
        System.out.println("==========================");
        System.out.println(GsonUtils.gsonString(resultData));
    }

}