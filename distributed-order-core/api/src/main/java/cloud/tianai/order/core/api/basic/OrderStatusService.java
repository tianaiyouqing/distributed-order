package cloud.tianai.order.core.api.basic;

import cloud.tianai.order.core.api.basic.enums.OrderStatusEnum;
import cloud.tianai.order.core.api.basic.exception.OrderStatusUpdateException;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 12:24
 * @Description: 基础的订单状态业务
 */
public interface OrderStatusService {

    /**
     * 更改订单状态
     *
     * @param oid               订单ID
     * @param updateOrderStatus 要修改的订单状态
     * @throws OrderStatusUpdateException 修改失败抛出异常
     */
    void updateOrderStatusForOid(String oid, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException;

    /**
     * 更改订单状态
     *
     * @param oid               订单ID
     * @param oldOrderStatus    旧的订单状态
     * @param updateOrderStatus 要修改的订单状态
     * @throws OrderStatusUpdateException 修改失败抛出异常
     */
    void updateOrderStatusForOid(String oid, OrderStatusEnum oldOrderStatus, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException;

    /**
     * 更改订单状态
     *
     * @param oid               订单ID
     * @param uid               用户ID
     * @param updateOrderStatus 要修改的订单状态
     * @throws OrderStatusUpdateException 修改失败抛出异常
     */
    void updateOrderStatusForOidAndUid(String oid, String uid, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException;

    /**
     * 更改订单状态
     *
     * @param oid            订单ID
     * @param uid            用户ID
     * @param oldOrderStatus 旧的订单状态
     * @param update         待修改的订单状态
     * @throws OrderStatusUpdateException 修改失败抛出异常
     */
    void updateOrderStatusForOidAndUid(String oid, String uid, OrderStatusEnum oldOrderStatus, OrderStatusEnum update) throws OrderStatusUpdateException;

    /**
     * 更改订单状态
     *
     * @param oid         订单ID
     * @param bid         用户ID
     * @param orderStatus 要修改的订单状态
     * @throws OrderStatusUpdateException 修改失败抛出异常
     */
    void updateOrderStatusForOidAndBid(String oid, String bid, OrderStatusEnum orderStatus) throws OrderStatusUpdateException;

    /**
     * 更改订单状态
     *
     * @param oid            订单ID
     * @param bid            用户ID
     * @param oldOrderStatus 旧的订单状态
     * @param update         要修改的订单状态
     * @throws OrderStatusUpdateException 修改失败抛出异常
     */
    void updateOrderStatusForOidAndBid(String oid, String bid, OrderStatusEnum oldOrderStatus, OrderStatusEnum update) throws OrderStatusUpdateException;
}
