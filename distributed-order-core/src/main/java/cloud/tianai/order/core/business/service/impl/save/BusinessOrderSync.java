package cloud.tianai.order.core.business.service.impl.save;

import cloud.tianai.order.core.dataobject.BusinessOrderDetailDO;
import cloud.tianai.order.core.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.exception.OrderSyncException;
import cloud.tianai.order.core.warpper.OrderWrapper;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 15:47
 * @Description: 订单同步接口
 */
public interface BusinessOrderSync {

    /**
     * 通过订单ID实现同步
     * @param oid
     * @throws OrderSyncException 订单同步异常
     */
    void sync(String oid) throws OrderSyncException;

    /**
     * 同步主表信息
     * @param orderMasterDO
     */
    void sync(BusinessOrderMasterDO orderMasterDO);

    /**
     * 同步详情信息
     * @param orderDetailDO
     */
    void sync(BusinessOrderDetailDO orderDetailDO);
    /**
     * 直接把数据复制到商家集群库中
     * @param orderWrapper
     * @throws OrderSyncException 订单同步异常
     */
    void sync(OrderWrapper orderWrapper) throws OrderSyncException;
}
