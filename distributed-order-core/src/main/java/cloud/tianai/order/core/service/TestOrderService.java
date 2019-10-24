package cloud.tianai.order.core.service;

import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.mapper.OrderDetailMapper;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: 天爱有情
 * @Date: 2019/10/24 15:06
 * @Description: 测试分库分表
 */
@Service
public class TestOrderService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;



    @Transactional(rollbackFor = Exception.class)
    public String add(OrderMasterDO orderMasterDO, OrderDetailDO orderDetailDO) {

        orderMasterMapper.insert(orderMasterDO);

//        int i = 1 / 0;
        orderDetailMapper.insert(orderDetailDO);

        System.out.println("aaa");

        return "success";
    }

}
