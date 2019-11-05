package cloud.tianai.order.api;

import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tianai.cloud.order.api.ProductStockService;
import tianai.cloud.order.api.dto.SimpleOrderProductDTO;
import tianai.cloud.order.api.response.ApiResponse;

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
