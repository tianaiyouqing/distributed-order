package cloud.tianai.order.core.api.basic;

import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/9 21:24
 * @Description: 基础订单搜索
 */
public interface BasicOrderSearchService {

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

}
