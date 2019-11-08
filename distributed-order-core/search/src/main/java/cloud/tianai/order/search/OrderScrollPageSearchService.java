package cloud.tianai.order.search;


import cloud.tianai.order.search.form.OrderSearchForm;

import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 16:22
 * @Description: 流式带有页码的跳板页
 */
public interface OrderScrollPageSearchService extends OrderScrollSearchService {

    /**
     * 未来的页码标识(最后一个订单号)
     * @param searchParam 搜索条件
     * @param lastFlowNum 当前页码的最后一个订单号
     * @param pageSize 分页数量
     * @param readNum 读取数量
     * @return List<String>
     */
    List<String> listFutureScrollPageNums(OrderSearchForm searchParam, String lastFlowNum, Integer pageSize, Integer readNum);

}
