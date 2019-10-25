package cloud.tianai.order.core.basic.impl;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.basic.OrderSaveService;
import cloud.tianai.order.core.basic.OrderStatusService;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.exception.OrderSaveException;
import cloud.tianai.order.core.exception.OrderStatusUpdateException;
import cloud.tianai.order.core.id.OrderIdGenerator;
import cloud.tianai.order.core.mapper.OrderDetailMapper;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import cloud.tianai.order.core.util.gson.GsonUtils;
import cloud.tianai.order.core.warpper.OrderWrapper;
import cloud.tianai.order.core.warpper.OrderWrappers;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    @Transactional(rollbackFor = Exception.class)
    protected OrderWrapper doInsertOrder(OrderWrapper orderWrapper) throws OrderSaveException {
        orderMasterMapper.insert(orderWrapper.getOrderMaster());
        orderDetailMapper.batchInsert(orderWrapper.getOrderDetail());
        return orderWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    public OrderWrapper getOrderDescForOid(String oid) {
        OrderMasterDO orderMasterDO = orderMasterMapper.selectById(oid);
        if(Objects.isNull(orderMasterDO)) {
            return null;
        }
        Collection<OrderDetailDO> orderDetailDOS = listOrderDetailsForOid(oid);
        if(CollectionUtils.isEmpty(orderDetailDOS)) {
            log.error("查询订单出错， 查到订单主信息， 没查到订单详情信息， 订单ID={}", oid);
            return null;
        }

        return OrderWrappers.wrap(orderMasterDO, orderDetailDOS);
    }

    private Collection<OrderDetailDO> listOrderDetailsForOid(String oid) {
        LambdaQueryWrapper<OrderDetailDO> queryWrapper = Wrappers
                .<OrderDetailDO>lambdaQuery()
                .eq(OrderDetailDO::getOid, oid)
                .orderByDesc(OrderDetailDO::getCreateTime);
        List<OrderDetailDO> orderDetailDOS = orderDetailMapper.selectList(queryWrapper);
        return orderDetailDOS;
    }

    @Override
    public OrderWrapper getOrderDescForOidAndUid(String oid, String uid) {
        LambdaQueryWrapper<OrderMasterDO> queryWrapper = Wrappers.<OrderMasterDO>lambdaQuery()
                .eq(OrderMasterDO::getOid, oid)
                .eq(OrderMasterDO::getUid, uid);

        OrderMasterDO orderMasterDO = orderMasterMapper.selectOne(queryWrapper);
        if(Objects.isNull(orderMasterDO)) {
            return null;
        }
        Collection<OrderDetailDO> orderDetailDOS = listOrderDetailsForOid(oid);
        if(CollectionUtils.isEmpty(orderDetailDOS)) {
            log.error("查询订单出错， 查到订单主信息， 没查到订单详情信息， 订单ID={}", oid);
            return null;
        }

        return OrderWrappers.wrap(orderMasterDO, orderDetailDOS);
    }

    @Override
    public OrderWrapper getOrderDescForOidAndBid(String oid, String bid) {
        LambdaQueryWrapper<OrderMasterDO> queryWrapper = Wrappers
                .<OrderMasterDO>lambdaQuery()
                .eq(OrderMasterDO::getOid, oid)
                .eq(OrderMasterDO::getBid, bid);

        OrderMasterDO orderMasterDO = orderMasterMapper.selectOne(queryWrapper);
        if(Objects.isNull(orderMasterDO)) {
            return null;
        }
        Collection<OrderDetailDO> orderDetailDOS = listOrderDetailsForOid(oid);
        if(CollectionUtils.isEmpty(orderDetailDOS)) {
            log.error("查询订单出错， 查到订单主信息， 没查到订单详情信息， 订单ID={}", oid);
            return null;
        }
        return OrderWrappers.wrap(orderMasterDO, orderDetailDOS);
    }
}
