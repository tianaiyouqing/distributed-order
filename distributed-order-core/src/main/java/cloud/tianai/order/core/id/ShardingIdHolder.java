package cloud.tianai.order.core.id;

/**
 * @Auther: 天爱有情
 * @Date: 2019/10/24 16:49
 * @Description: 分片ID持有者
 */
public interface ShardingIdHolder {

    /**
     * 获取用户分片 通过订单ID
     * @param oid 订单ID
     * @return 用户分片ID
     */
    String getUserShardingIdForOrderId(String oid);

    /**
     * 获取用户分片ID通过订单详情ID
     * @param orderDetailId 订单详情ID
     * @return 用户分片ID
     */
    String getUserShardingIdForOrderDetailId(String orderDetailId);

    /**
     * 获取商户分片ID通过订单ID
     * @param orderId 订单ID
     * @return 商户分片ID
     */
    String getBusinessShardingIdForOrderId(String orderId);

    /**
     * 获取商户分片ID通过订单详情ID
     * @param orderDetailId 订单详情ID
     * @return 商户分片ID
     */
    String getBusinessShardingIdForOrderDetailId(String orderDetailId);

    /**
     * 获取用户分片ID通过用户ID
     * @param uid 用户ID
     * @return 用户分片ID
     */
    String getShardingIdForUserId(String uid);

    /**
     * 获取商户分片ID通过商户ID
     * @param bid 商户ID
     * @return 商户分片ID
     */
    String getShardingIdForBusinessId(String bid);
}
