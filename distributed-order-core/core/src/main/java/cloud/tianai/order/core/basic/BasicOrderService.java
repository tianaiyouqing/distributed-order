package cloud.tianai.order.core.basic;

import cloud.tianai.order.core.api.basic.BasicOrderSearchService;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.core.basic.exception.OrderSaveException;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:54
 * @Description: 订单存储
 */
public interface BasicOrderService extends BasicOrderSearchService {

    /**
     * 更改订单信息 主信息和详情信息
     *
     * @param orderWrapper 订单信息
     * @throws OrderSaveException 修改失败抛出异常
     */
    void updateOrder(OrderWrapper orderWrapper) throws OrderSaveException;

    /**
     * 更改订单信息
     *
     * @param orderMaster 主信息
     * @throws OrderSaveException 更改失败异常
     */
    void updateOrder(OrderMasterDO orderMaster) throws OrderSaveException;

    /**
     * 更改订单详情信息
     *
     * @param orderDetailDOS 订单详情
     * @throws OrderSaveException 抛出异常
     */
    void updateOrder(Collection<OrderDetailDO> orderDetailDOS) throws OrderSaveException;

    /**
     * 更改订单详情信息
     *
     * @param orderDetailDO 订单详情
     * @throws OrderSaveException 更改失败异常
     */
    void updateOrder(OrderDetailDO orderDetailDO) throws OrderSaveException;

}
