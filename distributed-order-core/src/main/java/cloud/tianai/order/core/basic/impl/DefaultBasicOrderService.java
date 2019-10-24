package cloud.tianai.order.core.basic.impl;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.basic.OrderSaveService;
import cloud.tianai.order.core.basic.event.PostOrderInsertEvent;
import cloud.tianai.order.core.basic.event.PreOrderInsertEvent;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.exception.OrderSaveException;
import cloud.tianai.order.core.id.OrderIdGenerator;
import cloud.tianai.order.core.mapper.OrderDetailMapper;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import cloud.tianai.order.core.util.OrderCheckUtils;
import cloud.tianai.order.core.util.api.response.ApiResponse;
import cloud.tianai.order.core.util.api.response.ApiResponseStatusEnum;
import cloud.tianai.order.core.warpper.OrderWrapper;
import cloud.tianai.order.core.warpper.OrderWrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultBasicOrderService implements BasicOrderService, OrderSaveService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderIdGenerator orderIdGenerator;
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * 这里直接使用@Transactional注解即可， 逻辑是下的单只会下到一个库中，所以只需要使用对应库的事物即可， 这点shardingJdbc已经实现了
     * @param orderSaveForm 订单添加所需要的参数
     * @return OrderMasterAndOrderDetail
     * @throws OrderSaveException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderWrapper insertOrder(OrderSaveForm orderSaveForm) throws OrderSaveException {
        // 校验参数
        ApiResponse<?> checkResult = OrderCheckUtils.checkOrderSaveForm(orderSaveForm);
        if(!ApiResponseStatusEnum.SUCCESS.getCode().equals(checkResult.getCode())) {
            throw new OrderSaveException(checkResult.getMsg());
        }
        // 订单包装
        OrderWrapper orderWrapper = OrderWrappers.wrap(orderIdGenerator, orderSaveForm);
        applicationContext.publishEvent(new PreOrderInsertEvent(orderWrapper));
        OrderWrapper result = doInsertOrder(orderWrapper);
        applicationContext.publishEvent(new PostOrderInsertEvent(result));
        return result;
    }


    public OrderWrapper doInsertOrder(OrderWrapper orderWrapper) {
        orderMasterMapper.insert(orderWrapper.getOrderMaster());
        orderDetailMapper.batchInsert(orderWrapper.getOrderDetail());
        return orderWrapper;
    }
}
