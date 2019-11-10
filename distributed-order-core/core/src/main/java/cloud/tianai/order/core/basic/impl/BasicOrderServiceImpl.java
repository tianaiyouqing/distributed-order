package cloud.tianai.order.core.basic.impl;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.basic.BasicOrderSaveService;
import cloud.tianai.order.core.basic.BasicOrderStatusService;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.core.basic.exception.OrderSaveException;
import cloud.tianai.order.core.basic.exception.OrderStatusUpdateException;
import cloud.tianai.order.core.mapper.OrderDetailMapper;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import cloud.tianai.order.core.util.OrderMergeUtils;
import cloud.tianai.order.common.util.gson.GsonUtils;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.core.warpper.OrderWrappers;
import cloud.tianai.order.id.OrderIdGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 9:30
 * @Description: 基础订单业务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicOrderServiceImpl extends AbstractBasicOrderService implements BasicOrderService, BasicOrderSaveService, BasicOrderStatusService {

    private final OrderMasterMapper orderMasterMapper;
    private final OrderDetailMapper orderDetailMapper;

    /**
     * 订单ID生成器.
     */
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
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(OrderWrapper orderWrapper) throws OrderSaveException {
        updateOrder(orderWrapper.getOrderMaster());
        updateOrder(orderWrapper.getOrderDetail());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(OrderMasterDO orderMaster) throws OrderSaveException {
        OrderMasterDO oldData = orderMasterMapper.selectById(orderMaster.getOid());
        if (Objects.isNull(oldData)) {
            throw new OrderSaveException("修改订单失败， 订单未找到 [" + orderMaster.getOid() + "]");
        }
        OrderMasterDO newData = OrderMergeUtils.merge(oldData, orderMaster);
        newData.setUpdateTime(null);

        orderMasterMapper.updateById(newData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(Collection<OrderDetailDO> orderDetailDOS) throws OrderSaveException {
        List<String> orderDetailIds = orderDetailDOS
                .stream()
                .map(OrderDetailDO::getOrderDetailId)
                .collect(Collectors.toList());

        LambdaQueryWrapper<OrderDetailDO> queryWrapper = Wrappers
                .<OrderDetailDO>lambdaQuery()
                .in(OrderDetailDO::getOrderDetailId, orderDetailIds);
        List<OrderDetailDO> oldOrderDetails = orderDetailMapper.selectList(queryWrapper);

        if (!Objects.equals(orderDetailIds.size(), oldOrderDetails.size())) {
            throw new OrderSaveException("订单详情修改失败， 订单详情未全部找到， 详情ID [" + GsonUtils.gsonString(oldOrderDetails) + "]");
        }

        for (OrderDetailDO oldOrderDetail : oldOrderDetails) {
            Optional<OrderDetailDO> updateOptional = orderDetailDOS.stream()
                    .filter(o -> Objects.equals(o.getOrderDetailId(), oldOrderDetail.getOrderDetailId()))
                    .findFirst();
            if (updateOptional.isPresent()) {
                OrderDetailDO newOrderDetail = OrderMergeUtils.merge(oldOrderDetail, updateOptional.get());
                newOrderDetail.setUpdateTime(null);
                orderDetailMapper.updateById(newOrderDetail);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(OrderDetailDO orderDetailDO) throws OrderSaveException {
        updateOrder(Collections.singleton(orderDetailDO));
    }
    @Override
    public OrderWrapper getOrderDescForOid(String oid) {
        OrderMasterDO orderMasterDO = orderMasterMapper.selectById(oid);
        if (Objects.isNull(orderMasterDO)) {
            return null;
        }
        Collection<OrderDetailDO> orderDetailDOS = listOrderDetailsForOid(oid);
        if (CollectionUtils.isEmpty(orderDetailDOS)) {
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
        if (Objects.isNull(orderMasterDO)) {
            return null;
        }
        Collection<OrderDetailDO> orderDetailDOS = listOrderDetailsForOid(oid);
        if (CollectionUtils.isEmpty(orderDetailDOS)) {
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
        if (Objects.isNull(orderMasterDO)) {
            return null;
        }
        Collection<OrderDetailDO> orderDetailDOS = listOrderDetailsForOid(oid);
        if (CollectionUtils.isEmpty(orderDetailDOS)) {
            log.error("查询订单出错， 查到订单主信息， 没查到订单详情信息， 订单ID={}", oid);
            return null;
        }
        return OrderWrappers.wrap(orderMasterDO, orderDetailDOS);
    }


    @Override
    public OrderMasterDO getOrderMasterForOid(String oid) {
        return orderMasterMapper.selectById(oid);
    }

    @Override
    public Collection<OrderDetailDO> listOrderDetailForOid(String oid) {
        LambdaQueryWrapper<OrderDetailDO> queryWrapper = Wrappers.<OrderDetailDO>lambdaQuery().eq(OrderDetailDO::getOid, oid);
        List<OrderDetailDO> result = orderDetailMapper.selectList(queryWrapper);
        return result;
    }
}
