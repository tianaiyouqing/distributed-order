package cloud.tianai.order.core.api.pay;

import cloud.tianai.order.common.response.ApiResponse;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/8 13:36
 * @Description: 订单状态相关业务
 */
public interface OrderStatusService {

    ApiResponse<String> updateOrderStatus2OverhangByOid(String oid);

    ApiResponse<String> updateOrderStatus2FinishedByOid(String oid);

    ApiResponse<String> updateOrderStatus2CancelByOid(String oid);

    ApiResponse<String> updateOrderStatus2alreadyShippedByOidAndBid(String oid, String bid);

    ApiResponse<String> updateOrderStatus2ErrorByOidAndBid(String oid, String bid);

    ApiResponse<String> updateOrderStatus2ErrorFinishedByOidAndBid(String oid, String bid);

    ApiResponse<String> deleteOrderByOidAndUid(String oid, String uid);
}
