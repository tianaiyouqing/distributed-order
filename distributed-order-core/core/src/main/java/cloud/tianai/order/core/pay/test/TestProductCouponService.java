package cloud.tianai.order.core.pay.test;

import cloud.tianai.order.common.util.id.IdUtils;
import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.ProductCouponResult;
import cloud.tianai.order.core.sdk.ProductCouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestProductCouponService implements ProductCouponService {
    @Override
    public ProductCouponResult useCoupon(OrderCreateParamDTO orderCreateParamDTO) {
        String id = IdUtils.getNoRepetitionIdStr();
        ProductCouponResult productCouponResult = new ProductCouponResult();
        productCouponResult.setId(id);
        productCouponResult.setOtterCouponPrice(150L);
        return productCouponResult;
    }

    @Override
    public void rollback(ProductCouponResult productCouponResult) {
        log.info("coupon rollback");
    }
}
