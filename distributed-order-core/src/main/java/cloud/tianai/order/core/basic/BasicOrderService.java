package cloud.tianai.order.core.basic;

import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.exception.OrderSaveException;
import cloud.tianai.order.core.warpper.OrderWrapper;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:54
 * @Description: 订单存储
 */
public interface BasicOrderService {

    /**
     * 通过订单ID查询订单信息
     *
     * @param oid 订单ID
     * @return OrderWrapper
     */
    OrderWrapper getOrderDescForOid(String oid);

    /**
     * 通过订单ID和用户ID查询订单信息
     *
     * @param oid 订单ID
     * @param uid 用户ID
     * @return OrderWrapper
     */
    OrderWrapper getOrderDescForOidAndUid(String oid, String uid);

    /**
     * 通过订单ID和商家ID查询订单信息
     *
     * @param oid 订单ID
     * @param bid 店铺ID
     * @return OrderWrapper
     */
    OrderWrapper getOrderDescForOidAndBid(String oid, String bid);

    /**
     * 通过订单ID查询订单主表信息
     *
     * @param oid 订单ID
     * @return OrderMasterDO
     */
    OrderMasterDO getOrderMasterForOid(String oid);

    /**
     * 通过订单ID查询订单详情
     *
     * @param oid 订单ID
     * @return Collection<OrderDetailDO>
     */
    Collection<OrderDetailDO> listOrderDetailForOid(String oid);

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
