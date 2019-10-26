package cloud.tianai.order.core.mapper;

import cloud.tianai.order.core.basic.impl.AbstractOrderStatusService;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.search.form.OrderSearchForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 15:05
 * @Description: 订单主信息 dao
 */
public interface OrderMasterMapper extends BaseMapper<OrderMasterDO> {

    int updateOrderStatus(@Param("update") AbstractOrderStatusService.OrderStatusUpdate update);

    List<OrderMasterDO> findtestSearch();

    List<String> findFutureFlowPageNums(@Param("search") OrderSearchForm search,
                                    @Param("lastFlowNum") String lastFlowNum,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("readNum") Integer readNum);

    List<OrderMasterDO> findForFlow(@Param("search") OrderSearchForm search,
                                    @Param("lastFlowNum") String lastFlowNum,
                                    @Param("pageSize") Integer pageSize);
}
