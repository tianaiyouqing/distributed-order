package cloud.tianai.order.api.test;

import cloud.tianai.order.core.util.id.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tianai.cloud.order.api.ProductCouponService;
import tianai.cloud.order.api.dto.OrderCreateParamDTO;
import tianai.cloud.order.api.dto.ProductCouponResult;

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
