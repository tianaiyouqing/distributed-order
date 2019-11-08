package cloud.tianai.order.core.api;

import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.common.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.sdk.ProductStockService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
//@Service
public class TestProductStockService implements ProductStockService {
    @Override
    public ApiResponse<String> stock(Collection<SimpleOrderProductDTO> productDTOS) {

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
