package cloud.tianai.order.id;

import cloud.tianai.order.common.util.id.Sequence;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 16:52
 * @Description: 订单ID生成器
 */
public class DefaultOrderIdGenerator implements OrderIdGenerator, ShardingIdHolder {

    private static final Integer VERSION_ID = 1;

    private static final Integer UID_SPLIT_BIT = 2;
    private static final Integer BID_SPLIT_BIT = 2;

    private static final Sequence worker = new Sequence();

    /**
     * 订单ID
     *  1位版本ID + 19位雪花算法ID + 2位用户ID + 2位商户ID + 1位自增ID
     * @param uid
     * @param bid
     * @return
     */
    @Override
    public String generatorOrderId(String uid, String bid) {
        if(StringUtils.isBlank(uid) || StringUtils.isBlank(bid)) {
            throw new IllegalArgumentException("生成订单ID错误. uid 或者 bid 不能为空");
        }
        if(bid.length() < UID_SPLIT_BIT || uid.length() < BID_SPLIT_BIT) {
            throw new IllegalArgumentException("生成订单ID错误. uid 的长度必须大于"
                    + UID_SPLIT_BIT
                    +", bid的长度必须大于"
                    + BID_SPLIT_BIT
                    + "[" + uid +"," + bid +"]"
            );
        }
        String uidSplit = uid.substring(uid.length() - UID_SPLIT_BIT);
        String bidSplit = bid.substring(bid.length() - BID_SPLIT_BIT);
        long workerId = worker.nextId();

        StringBuilder orderId = new StringBuilder();
        orderId.append(VERSION_ID);
        orderId.append(workerId);
        orderId.append(uidSplit);
        orderId.append(bidSplit);
        orderId.append(0);

        return orderId.toString();
    }

    /**
     * 生成订单详情ID
     * @param oid 订单ID
     * @return String
     */
    @Override
    public String generatorOrderDetailId(String oid) {
        checkOrderId(oid);

        String oidStart = oid.substring(0, oid.length() - 1);
        String oidEnd = oid.substring(oid.length() - 1);
        Integer oidAutoNum;
        try {
            oidAutoNum = Integer.valueOf(oidEnd);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("生成订单详情ID错误.订单编号自增序号不能转换成整数 [" + oid + "]");
        }
        oidAutoNum++;
        return oidStart + oidAutoNum;
    }

    @Override
    public String generatorOrderDetailId(String prevOrderDetailId, String oid) {
        if(StringUtils.isBlank(prevOrderDetailId)) {
            return generatorOrderDetailId(oid);
        }
        return nextOrderDetailId(prevOrderDetailId);
    }

    private void checkOrderId(String oid) {
        if(StringUtils.isBlank(oid) || oid.length() < 25) {
            throw new IllegalArgumentException("生成订单详情ID错误. 订单ID错误 [" + oid + "]");
        }
    }

    /**
     * 通过订单详情ID获取下一个订单详情ID
     * @param orderDetailId 订单详情ID
     * @return String
     */
    @Override
    public String nextOrderDetailId(String orderDetailId) {
        return generatorOrderDetailId(orderDetailId);
    }


    /**
     * 获取用户分片ID 通过订单ID
     * @param oid 订单ID
     * @return String
     */
    @Override
    public String getUserShardingIdForOrderId(String oid) {
        checkOrderId(oid);
        String sub = oid.substring(oid.length() - 5, oid.length() - 3);
        return sub;
    }

    /**
     * 获取用户分片ID通过订单详情ID
     * @param orderDetailId 订单详情ID
     * @return String
     */
    @Override
    public String getUserShardingIdForOrderDetailId(String orderDetailId) {
        checkOrderId(orderDetailId);
        String sub = orderDetailId.substring(orderDetailId.length() - 5, orderDetailId.length() - 3);
        return sub;
    }

    /**
     * 获取商户分片ID通过订单ID
     * @param oid 订单ID
     * @return
     */
    @Override
    public String getBusinessShardingIdForOrderId(String oid) {
        checkOrderId(oid);
        String sub = oid.substring(oid.length() - 3, oid.length() - 1);
        return sub;
    }

    /**
     * 获取商户分片ID通过订单详情ID
     * @param orderDetailId 订单详情ID
     * @return String
     */
    @Override
    public String getBusinessShardingIdForOrderDetailId(String orderDetailId) {
        checkOrderId(orderDetailId);
        String sub = orderDetailId.substring(orderDetailId.length() - 3, orderDetailId.length() - 1);
        return sub;
    }

    /**
     * 通过用户ID获取用户分片ID
     * @param uid 用户ID
     * @return String
     */
    @Override
    public String getShardingIdForUserId(String uid) {
        if(StringUtils.isBlank(uid) || uid.length() < UID_SPLIT_BIT) {
            throw new IllegalArgumentException("通过用户ID截取分片ID出错， 用户ID不能为空， 或者用户ID的长度必须大于"
                    + UID_SPLIT_BIT
                    +"[" + uid +"]"
            );
        }
        String uidSplit = uid.substring(uid.length() - UID_SPLIT_BIT);
        return uidSplit;
    }

    /**
     * 通过商户ID获取商户分片ID
     * @param bid 商户ID
     * @return String
     */
    @Override
    public String getShardingIdForBusinessId(String bid) {
        if(StringUtils.isBlank(bid) || bid.length() < BID_SPLIT_BIT) {
            throw new IllegalArgumentException("通过商家ID截取分片ID出错， 商家ID不能为空， 或者商家ID的长度必须大于"
                    + BID_SPLIT_BIT
                    +"[" + bid +"]"
            );
        }
        String bidSplit = bid.substring(bid.length() - BID_SPLIT_BIT);
        return bidSplit;
    }
}
