package cloud.tianai.order.core.search;

import cloud.tianai.order.core.dto.OrderMasterDTO;
import cloud.tianai.order.core.search.form.OrderSearchForm;

import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 15:41
 * @Description: 流式查询接口
 */
public interface OrderScrollSearchService {

    /**
     * 跳板查询
     *
     * @param searchParam 搜索参数
     * @param lastFlowNum 流式页码的切点
     * @param pageSize   分页数量
     * @return List<OrderMasterDO>
     */
    List<OrderMasterDTO> scrollSearch(OrderSearchForm searchParam, String lastFlowNum, Integer pageSize);
}
