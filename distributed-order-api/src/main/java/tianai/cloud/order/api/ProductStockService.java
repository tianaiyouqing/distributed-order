package tianai.cloud.order.api;

import tianai.cloud.order.api.dto.SimpleOrderProductDTO;
import tianai.cloud.order.api.response.ApiResponse;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 15:20
 * @Description: 商品库存接口
 */
public interface ProductStockService {


    /**
     * 库存扣除
     * @param productDTOS
     * @return 如果成功，返回stockID
     */
    ApiResponse<String> stock(Collection<SimpleOrderProductDTO> productDTOS);

    /**
     * 通过stockID 确认库存
     * @param stockId 库存ID
     */
    void commit(String stockId);

    /**
     * 通过stockID回滚库存
     * @param stockId 库存ID
     */
    void rollback(String stockId);
}
