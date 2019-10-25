package cloud.tianai.order.core.warpper.impl;

import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.warpper.OrderWrapper;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 16:46
 * @Description: 基本订单数据包装器
 */
@Accessors(chain = true)
@NoArgsConstructor
public class BasicOrderWrapper implements OrderWrapper {

    @Setter
    private OrderMasterDO orderMasterDO;
    @Setter
    private Collection<OrderDetailDO> orderDetailDOS;

    public BasicOrderWrapper(OrderMasterDO orderMasterDO, Collection<OrderDetailDO> orderDetailDOS) {
        this.orderMasterDO = orderMasterDO;
        this.orderDetailDOS = orderDetailDOS;
    }

    @Override
    public OrderMasterDO getOrderMaster() {
        return orderMasterDO;
    }

    @Override
    public Collection<OrderDetailDO> getOrderDetail() {
        return orderDetailDOS;
    }
}
