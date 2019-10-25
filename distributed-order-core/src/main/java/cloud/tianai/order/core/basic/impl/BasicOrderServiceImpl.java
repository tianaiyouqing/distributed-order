package cloud.tianai.order.core.basic.impl;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.basic.OrderSaveService;
import cloud.tianai.order.core.basic.OrderStatusService;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.exception.OrderSaveException;
import cloud.tianai.order.core.exception.OrderStatusUpdateException;
import cloud.tianai.order.core.id.OrderIdGenerator;
import cloud.tianai.order.core.mapper.OrderDetailMapper;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import cloud.tianai.order.core.util.gson.GsonUtils;
import cloud.tianai.order.core.warpper.OrderWrapper;
import cloud.tianai.order.core.warpper.OrderWrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 9:30
 * @Description: 基础订单业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicOrderServiceImpl extends AbstractBasicOrderService implements BasicOrderService, OrderSaveService, OrderStatusService {


    private final OrderMasterMapper orderMasterMapper;
    private final OrderDetailMapper orderDetailMapper;

    /** 订单ID生成器. */
    private final OrderIdGenerator orderIdGenerator;

    private final ApplicationContext applicationContext;


    @Override
    protected OrderWrapper wrapSaveOrder(OrderIdGenerator orderIdGenerator, OrderSaveForm orderSaveForm) {
        return OrderWrappers.wrap(orderIdGenerator, orderSaveForm);
    }

    @Override
    protected OrderWrapper doInsertOrder(OrderWrapper orderWrapper) throws OrderSaveException {
        orderMasterMapper.insert(orderWrapper.getOrderMaster());
        orderDetailMapper.batchInsert(orderWrapper.getOrderDetail());

        return orderWrapper;
    }

    @Override
    protected boolean doUpdateOrderStatus(OrderStatusUpdate update) throws OrderStatusUpdateException {
        int index = orderMasterMapper.updateOrderStatus(update);
        return index > 0;
    }


    @Override
    protected OrderIdGenerator getOrderIdGenerator() {
        return this.orderIdGenerator;
    }

    @Override
    protected ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}
