package cloud.tianai.order.core.sdk;


import cloud.tianai.order.core.sdk.dto.OrderPromotionParam;
import cloud.tianai.order.core.sdk.dto.ProductCouponResult;
import cloud.tianai.order.core.sdk.dto.PromotionInfoDTO;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 15:00
 * @Description: 商品优惠业务调用接口
 */
public interface PromotionService {


    /**
     * 使用折扣
     * @param promotionParam 订单创建的参数给优惠券系统进行匹配优惠
     * @return ProductCouponResult
     */
    PromotionInfoDTO usePromotion(OrderPromotionParam promotionParam);

    void rollback(PromotionInfoDTO promotionParam);
}
