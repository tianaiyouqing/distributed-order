package cloud.tianai.order.core.sdk;


import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.ProductCouponResult;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 15:00
 * @Description: 商品优惠券业务调用接口
 */
public interface ProductCouponService  {


    /**
     * 使用优惠券
     * @param orderCreateParamDTO 订单创建的参数给优惠券系统进行匹配优惠
     * @return ProductCouponResult
     */
    ProductCouponResult useCoupon(OrderCreateParamDTO orderCreateParamDTO);

    void rollback(ProductCouponResult productCouponResult);
}
