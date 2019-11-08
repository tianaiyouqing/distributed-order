package cloud.tianai.order.business.api.sync;


import cloud.tianai.order.business.common.exception.OrderSyncException;
import cloud.tianai.order.business.common.dataobject.BusinessOrderDetailDO;
import cloud.tianai.order.business.common.dataobject.BusinessOrderMasterDO;

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
}
