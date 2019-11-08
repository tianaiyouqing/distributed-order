package cloud.tianai.order.business.core.service.impl.save.impl;


import cloud.tianai.order.business.common.exception.OrderSyncException;
import cloud.tianai.order.business.core.util.converter.BusinessOrderDetailConverter;
import cloud.tianai.order.business.core.util.converter.BusinessOrderMasterConverter;
import cloud.tianai.order.business.common.dataobject.BusinessOrderDetailDO;
import cloud.tianai.order.business.common.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.business.core.event.OrderSyncForDeleteEvent;
import cloud.tianai.order.business.core.event.OrderSyncForInsertEvent;
import cloud.tianai.order.business.core.event.OrderSyncForUpdateEvent;
import cloud.tianai.order.business.core.mapper.BusinessOrderDetailMapper;
import cloud.tianai.order.business.core.mapper.BusinessOrderMasterMapper;
import cloud.tianai.order.business.api.sync.BusinessOrderSync;
import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.common.response.ApiResponseStatusEnum;
import cloud.tianai.order.core.api.basic.BasicOrderService;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.core.common.wrapper.BasicOrderWrapper;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.lock.LockTemplate;
import cloud.tianai.order.lock.dto.LockDTO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 15:49
 * @Description: 商家订单数据同步
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessOrderSyncImpl implements BusinessOrderSync {

    private final LockTemplate lockTemplate;
    private final BasicOrderService basicOrderService;
    private final BusinessOrderMasterMapper businessOrderMasterMapper;
    private final BusinessOrderDetailMapper businessOrderDetailMapper;
    private final ApplicationContext applicationContext;

    private final BusinessOrderMasterConverter businessOrderMasterConverter = new BusinessOrderMasterConverter();
    private final BusinessOrderDetailConverter businessOrderDetailConverter = new BusinessOrderDetailConverter();

    private static final String SYNC_ORDER_KEY_PREFIX = "business:order:sync:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sync(String oid) throws OrderSyncException {
        OrderWrapper orderWrapperResponse = basicOrderService.getOrderDescForOid(oid);
        if(orderWrapperResponse == null) {
            // 如果是空，则执行删除操作
            businessOrderMasterMapper.deleteById(oid);
            businessOrderDetailMapper.delete(Wrappers.<BusinessOrderDetailDO>lambdaQuery().eq(BusinessOrderDetailDO::getOid, oid));

            // 发送删除事件
            applicationContext.publishEvent(new OrderSyncForDeleteEvent(oid));
            return;
        }
        // 否则执行插入/更新操作
        sync(orderWrapperResponse);
    }

    @Override
    public void sync(BusinessOrderMasterDO orderMasterDO) {
        LockDTO lockDTO = lock(orderMasterDO.getOid());
        BusinessOrderMasterDO searchRes = businessOrderMasterMapper.selectById(orderMasterDO.getOid());
        try {
            if(Objects.isNull(searchRes)) {
                businessOrderMasterMapper.insert(orderMasterDO);
                // 发送插入事件
                applicationContext.publishEvent(new OrderSyncForInsertEvent(orderMasterDO.getOid()));
            }else {
                businessOrderMasterMapper.updateById(orderMasterDO);
                // 发送修改事件
                applicationContext.publishEvent(new OrderSyncForUpdateEvent(orderMasterDO.getOid()));
            }
        } finally {
            unLock(lockDTO);
        }
    }

    @Override
    public void sync(BusinessOrderDetailDO orderDetailDO) {
        LockDTO lockDTO = lock(orderDetailDO.getOid());

        try {
            BusinessOrderDetailDO searchRes = businessOrderDetailMapper.selectById(orderDetailDO.getOrderDetailId());
            if(Objects.isNull(searchRes)) {
                businessOrderDetailMapper.insert(orderDetailDO);
                // 发送插入事件
                applicationContext.publishEvent(new OrderSyncForInsertEvent(orderDetailDO.getOid()));
            }else {
                businessOrderDetailMapper.updateById(orderDetailDO);
                // 发送修改事件
                applicationContext.publishEvent(new OrderSyncForUpdateEvent(orderDetailDO.getOid()));
            }
        } finally {
            unLock(lockDTO);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void sync(OrderWrapper orderWrapper) throws OrderSyncException{
        OrderMasterDO orderMaster = orderWrapper.getOrderMaster();
        Collection<OrderDetailDO> orderDetail = orderWrapper.getOrderDetail();
        LockDTO lockDTO = lock(orderMaster.getOid());
        try {
            BusinessOrderMasterDO searchRes = businessOrderMasterMapper.selectById(orderMaster.getOid());
            if(Objects.isNull(searchRes)) {
                businessOrderMasterMapper.insert(businessOrderMasterConverter.convert(orderMaster));
                businessOrderDetailMapper.batchInsert(businessOrderDetailConverter.convert(orderDetail));
                // 发送插入事件
                applicationContext.publishEvent(new OrderSyncForInsertEvent(orderMaster.getOid()));
            }else {
                businessOrderMasterMapper.updateById(businessOrderMasterConverter.convert(orderMaster));
                for (OrderDetailDO orderDetailDO : orderDetail) {
                    businessOrderDetailMapper.updateById(businessOrderDetailConverter.convert(orderDetailDO));
                }
                // 发送修改事件
                applicationContext.publishEvent(new OrderSyncForUpdateEvent(orderMaster.getOid()));
            }
        } finally {
            unLock(lockDTO);
        }
    }


    private void unLock(LockDTO lockDTO) {
        lockTemplate.unLock(lockDTO);
    }

    private LockDTO lock(String oid) {
        LockDTO lockDTO = lockTemplate.sleepLock(SYNC_ORDER_KEY_PREFIX + oid);
        if(Objects.isNull(oid)) {
            throw new OrderSyncException("同步失败， 加锁失败， oid: " + oid);
        }
        return lockDTO;
    }
}
