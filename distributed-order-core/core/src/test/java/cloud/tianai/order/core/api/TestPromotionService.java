package cloud.tianai.order.core.api;

import cloud.tianai.order.common.util.id.IdUtils;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.sdk.dto.OrderPromotionParam;
import cloud.tianai.order.core.sdk.dto.ProductCouponResult;
import cloud.tianai.order.core.sdk.PromotionService;
import cloud.tianai.order.core.sdk.dto.PromotionInfoDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Service
public class TestPromotionService implements PromotionService {

    @Override
    public PromotionInfoDTO usePromotion(OrderPromotionParam promotionParam) {
        return null;
    }

    @Override
    public void rollback(PromotionInfoDTO promotionParam) {

    }
}
