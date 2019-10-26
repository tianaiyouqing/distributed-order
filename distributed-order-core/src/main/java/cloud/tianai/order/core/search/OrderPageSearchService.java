package cloud.tianai.order.core.search;

import cloud.tianai.order.core.dto.OrderMasterDTO;
import cloud.tianai.order.core.search.form.OrderSearchForm;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 16:31
 * @Description: 订单分页搜索业务层接口
 */
public interface OrderPageSearchService extends OrderScrollPageSearchService {

    /**
     * 普通的分页查询
     *
     * @param search   搜索条件
     * @param pageNum  当前页
     * @param pageSize 每页显示数量
     * @return Page<OrderMasterDTO>
     */
    IPage<OrderMasterDTO> listOrderForPage(OrderSearchForm search, Integer pageNum, Integer pageSize);
}
