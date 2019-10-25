package cloud.tianai.order.core.id;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 16:42
 * @Description: 订单ID生成器
 */
public interface OrderIdGenerator {

    /**
     * 生成订单ID
     * @param uid 用户ID
     * @param bid 商户ID
     * @return 订单ID
     */
    String generatorOrderId(String uid, String bid);

    /**
     * 生成订单详情ID
     * @param oid 订单ID
     * @return 订单详情ID
     */
    String generatorOrderDetailId(String oid);

    /**
     * 生成订单详情ID
     * @param prevOrderDetailId 上一个订单详情ID， 可为空
     * @param oid 订单ID
     * @return String
     */
    String generatorOrderDetailId(String prevOrderDetailId, String oid);

    /**
     * 获取下一个订单详情ID
     * @param orderDetailId 订单详情ID
     * @return 订单详情ID
     */
    String nextOrderDetailId(String orderDetailId);


}
