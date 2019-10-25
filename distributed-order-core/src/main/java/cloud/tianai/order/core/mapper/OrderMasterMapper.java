package cloud.tianai.order.core.mapper;

import cloud.tianai.order.core.basic.impl.AbstractOrderStatusService;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 15:05
 * @Description: 订单主信息 dao
 */
public interface OrderMasterMapper extends BaseMapper<OrderMasterDO> {

    int updateOrderStatus(@Param("update") AbstractOrderStatusService.OrderStatusUpdate update);
}
