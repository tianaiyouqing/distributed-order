package cloud.tianai.order.core.basic.impl;

import cloud.tianai.order.core.basic.OrderStatusService;
import cloud.tianai.order.core.basic.event.PostOrderStatusUpdateEvent;
import cloud.tianai.order.core.basic.event.PreOrderStatusUpdateEvent;
import cloud.tianai.order.core.enums.OrderStatusEnum;
import cloud.tianai.order.core.exception.OrderStatusUpdateException;
import cloud.tianai.order.core.util.gson.GsonUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 13:43
 * @Description: 订单状态基础业务功能
 */
@Slf4j
public abstract class AbstractOrderStatusService extends AbstractOrderSaveService implements OrderStatusService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusForOid(String oid, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException {
        Assert.hasText(oid, "订单ID不能为空");
        Assert.notNull(updateOrderStatus, "订单状态不能为空");

        OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate().setOid(oid).setUpdateOrderStatus(updateOrderStatus.getCode());
        updateOrderStatus(orderStatusUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusForOid(String oid, OrderStatusEnum oldOrderStatus, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException {
        Assert.hasText(oid, "订单ID不能为空");
        Assert.notNull(oldOrderStatus, "原始订单状态不能为空");
        Assert.notNull(updateOrderStatus, "订单状态不能为空");
        OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate()
                .setOid(oid)
                .setOldOrderStatus(oldOrderStatus.getCode())
                .setUpdateOrderStatus(updateOrderStatus.getCode());

        updateOrderStatus(orderStatusUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusForOidAndUid(String oid, String uid, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException {
        Assert.hasText(oid, "订单ID不能为空");
        Assert.hasText(uid, "用户ID不能为空");
        Assert.notNull(updateOrderStatus, "订单状态不能为空");
        OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate()
                .setOid(oid)
                .setUid(uid)
                .setUpdateOrderStatus(updateOrderStatus.getCode());

        updateOrderStatus(orderStatusUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusForOidAndUid(String oid, String uid, OrderStatusEnum oldOrderStatus, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException {
        Assert.hasText(oid, "订单ID不能为空");
        Assert.hasText(uid, "用户ID不能为空");
        Assert.notNull(oldOrderStatus, "原始订单状态不能为空");
        Assert.notNull(updateOrderStatus, "订单状态不能为空");
        OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate()
                .setOid(oid)
                .setUid(uid)
                .setOldOrderStatus(oldOrderStatus.getCode())
                .setUpdateOrderStatus(updateOrderStatus.getCode());

        updateOrderStatus(orderStatusUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusForOidAndBid(String oid, String bid, OrderStatusEnum orderStatus) throws OrderStatusUpdateException {
        Assert.hasText(oid, "订单ID不能为空");
        Assert.hasText(bid, "商户ID不能为空");
        Assert.notNull(orderStatus, "订单状态不能为空");
        OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate()
                .setOid(oid)
                .setBid(bid)
                .setUpdateOrderStatus(orderStatus.getCode());

        updateOrderStatus(orderStatusUpdate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatusForOidAndBid(String oid, String bid, OrderStatusEnum oldOrderStatus, OrderStatusEnum updateOrderStatus) throws OrderStatusUpdateException {
        Assert.hasText(oid, "订单ID不能为空");
        Assert.hasText(bid, "商户ID不能为空");
        Assert.notNull(oldOrderStatus, "原始订单状态不能为空");
        Assert.notNull(updateOrderStatus, "订单状态不能为空");

        OrderStatusUpdate orderStatusUpdate = new OrderStatusUpdate()
                .setOid(oid)
                .setBid(bid)
                .setOldOrderStatus(oldOrderStatus.getCode())
                .setUpdateOrderStatus(updateOrderStatus.getCode());

        updateOrderStatus(orderStatusUpdate);
    }


    /**
     * 更新订单状态
     *
     * @param update 参数
     * @throws OrderStatusUpdateException 更改失败抛出异常
     */
    @Transactional(rollbackFor = Exception.class)
    protected void updateOrderStatus(OrderStatusUpdate update) throws OrderStatusUpdateException {
        update = postProcessBeforeUpdateOrderStatus(update);

        boolean updateStatus = doUpdateOrderStatus(update);

        postProcessAfterUpdateOrderStatus(update, updateStatus);
        if (!updateStatus) {
            log.error("修改订单失败， 搜索条件未扫描到对应的订单, update={}", GsonUtils.gsonString(update));
            throw new OrderStatusUpdateException("修改失败， 搜索条件不正确");
        }

    }

    /**
     * 在修改订单状态之后
     *
     * @param update       要修改的订单状态和查询参数
     * @param updateStatus 是否修改成功
     */
    void postProcessAfterUpdateOrderStatus(OrderStatusUpdate update, boolean updateStatus) {
        getApplicationContext().publishEvent(new PostOrderStatusUpdateEvent(update, updateStatus));
    }

    /**
     * 在修改订单状态之前
     *
     * @param update 要修改的订单状态和查询参数
     * @return
     */
    OrderStatusUpdate postProcessBeforeUpdateOrderStatus(OrderStatusUpdate update) {
        getApplicationContext().publishEvent(new PreOrderStatusUpdateEvent(update));
        return update;
    }


    /**
     * 修改订单状态
     *
     * @param update 要修改订单状态的相关参数
     * @return 修改成功返回true
     * @throws OrderStatusUpdateException
     */
    protected abstract boolean doUpdateOrderStatus(OrderStatusUpdate update) throws OrderStatusUpdateException;


    @Data
    @Accessors(chain = true)
    public static class OrderStatusUpdate {

        // 查询条件
        /**
         * 订单ID.
         */
        private String oid;
        /**
         * 用户ID.
         */
        private String uid;
        /**
         * 商户ID.
         */
        private String bid;
        /**
         * 原始的订单状态.
         */
        private Integer oldOrderStatus;

        /**
         * 要修改的订单状态.
         */
        private Integer updateOrderStatus;
    }
}
