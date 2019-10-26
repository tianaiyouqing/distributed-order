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

import static org.junit.Assert.*;

public class UserOrderPageSearchServiceImplTest extends ApplicationTests {


    @Autowired
    private UserOrderPageSearchServiceImpl userOrderPageSearchService;
    @Autowired
    private OrderMasterMapper orderMasterMapper;


    @Test
    public void test1() {
        List<OrderMasterDO> result = orderMasterMapper.findtestSearch();
        System.out.println(result);
    }

    @Test
    public void flowSearchTest() {
        Integer pageSize = 20;
        OrderSearchForm search = new OrderSearchForm();
        search.setUid("1187398437650337793");
        String lastFlowNum = null;
        List<String> lastFlowNums = userOrderPageSearchService.listFutureFlowPageNums(search, lastFlowNum, pageSize, 10);
        System.out.println(GsonUtils.gsonString(lastFlowNums));

        List<OrderMasterDTO> resultData =  new ArrayList<>(255);
        List<OrderMasterDTO> result = userOrderPageSearchService.flowSearch(search, lastFlowNum, pageSize);
        resultData.addAll(result);
        for (String flowNum : lastFlowNums) {
            lastFlowNum = flowNum;
            result = userOrderPageSearchService.flowSearch(search, lastFlowNum, pageSize);
            resultData.addAll(result);
        }


        System.out.println("==========================");
        System.out.println(GsonUtils.gsonString(resultData));
    }

}