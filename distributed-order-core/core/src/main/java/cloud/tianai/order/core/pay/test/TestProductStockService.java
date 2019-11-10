package cloud.tianai.order.core.pay.test;

import cloud.tianai.order.core.api.pay.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.sdk.ProductStockService;
import cloud.tianai.order.core.sdk.dto.ProductReduceStockDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import cloud.tianai.order.common.response.ApiResponse;

import java.util.Collection;

@Slf4j
@Service
public class TestProductStockService implements ProductStockService {

    @Override
    public ApiResponse<String> reduceStock(Collection<ProductReduceStockDTO> productDTOS) {
        return ApiResponse.ofSuccess("stockId:xxx1");
    }

    @Override
    public void commit(String stockId) {
        log.info("stock commit id is {}", stockId);
    }

    @Override
    public void rollback(String stockId) {
        log.info("stock rollback id is {}", stockId);
    }
}
