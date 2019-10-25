package cloud.tianai.order.core.basic;

import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.warpper.OrderWrapper;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:54
 * @Description: 订单存储
 */
public interface BasicOrderService {

    /**
     * 通过订单ID查询订单信息
     * @param oid 订单ID
     * @return OrderWrapper
     */
    OrderWrapper getOrderDescForOid(String oid);

    /**
     * 通过订单ID和用户ID查询订单信息
     * @param oid 订单ID
     * @param uid 用户ID
     * @return OrderWrapper
     */
    OrderWrapper getOrderDescForOidAndUid(String oid, String uid);

    /**
     * 通过订单ID和商家ID查询订单信息
     * @param oid 订单ID
     * @param bid 店铺ID
     * @return OrderWrapper
     */
    OrderWrapper getOrderDescForOidAndBid(String oid, String bid);

}
