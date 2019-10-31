package cloud.tianai.order.core.business.mapper;

import cloud.tianai.order.core.dataobject.BusinessOrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 15:05
 * @Description: 订单详情 dao
 */
public interface BusinessOrderDetailMapper extends BaseMapper<BusinessOrderDetailDO> {

    int batchInsert(@Param("orderDetails") Collection<BusinessOrderDetailDO> orderDetail);
}
