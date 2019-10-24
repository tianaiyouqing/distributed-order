package cloud.tianai.order.core.information;

import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @Auther: 天爱有情
 * @Date: 2019/10/24 19:10
 * @Description: 订单主信息和订单详情
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMasterAndOrderDetail {

    /** 订单主信息. */
    private OrderMasterDO orderMasterDO;
    /** 订单详情. */
    private Collection<OrderDetailDO> orderDetailDOS;

}
