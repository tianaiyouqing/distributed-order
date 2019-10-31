package cloud.tianai.order.core.business.service.impl.save.impl;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.business.mapper.BusinessOrderDetailMapper;
import cloud.tianai.order.core.business.mapper.BusinessOrderMasterMapper;
import cloud.tianai.order.core.business.service.impl.save.BusinessOrderSync;
import cloud.tianai.order.core.dataobject.BusinessOrderDetailDO;
import cloud.tianai.order.core.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.exception.OrderSyncException;
import cloud.tianai.order.core.util.converter.BusinessOrderDetailConverter;
import cloud.tianai.order.core.util.converter.BusinessOrderMasterConverter;
import cloud.tianai.order.core.warpper.OrderWrapper;
import com.xjcod.commons.lock.LockTemplate;
import com.xjcod.commons.lock.dto.LockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final BusinessOrderMasterConverter businessOrderMasterConverter = new BusinessOrderMasterConverter();
    private final BusinessOrderDetailConverter businessOrderDetailConverter = new BusinessOrderDetailConverter();

    private static final String SYNC_ORDER_KEY_PREFIX = "business:order:sync:";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sync(String oid) throws OrderSyncException {
        OrderWrapper orderWrapper = basicOrderService.getOrderDescForOid(oid);
        sync(orderWrapper);
    }

    @Override
    public void sync(BusinessOrderMasterDO orderMasterDO) {
        LockDTO lockDTO = lock(orderMasterDO.getOid());
        BusinessOrderMasterDO searchRes = businessOrderMasterMapper.selectById(orderMasterDO.getOid());
        try {
            if(Objects.isNull(searchRes)) {
                businessOrderMasterMapper.insert(orderMasterDO);
            }else {
                businessOrderMasterMapper.updateById(orderMasterDO);
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
            }else {
                businessOrderDetailMapper.updateById(orderDetailDO);
            }
        } finally {
            unLock(lockDTO);
        }
    }

    @Override
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
            }else {
                businessOrderMasterMapper.updateById(businessOrderMasterConverter.convert(orderMaster));
                for (OrderDetailDO orderDetailDO : orderDetail) {
                    businessOrderDetailMapper.updateById(businessOrderDetailConverter.convert(orderDetailDO));
                }
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
