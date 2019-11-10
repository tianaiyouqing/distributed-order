package cloud.tianai.order.core.pay.test;

import cloud.tianai.order.common.util.id.IdUtils;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.sdk.dto.OrderPromotionParam;
import cloud.tianai.order.core.sdk.dto.ProductCouponResult;
import cloud.tianai.order.core.sdk.PromotionService;
import cloud.tianai.order.core.sdk.dto.PromotionInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestPromotionService implements PromotionService {

    @Override
    public PromotionInfoDTO usePromotion(OrderPromotionParam orderCreateParam) {
        String id = IdUtils.getNoRepetitionIdStr();
        PromotionInfoDTO productCouponResult = new PromotionInfoDTO();
        productCouponResult.setId(id);
        productCouponResult.setPromotionId(orderCreateParam.getPromotionId());
        productCouponResult.setDiscountFee(150L);
        return productCouponResult;
    }

    @Override
    public  void rollback(PromotionInfoDTO promotionParam) {
        log.info("coupon rollback");
    }
}
