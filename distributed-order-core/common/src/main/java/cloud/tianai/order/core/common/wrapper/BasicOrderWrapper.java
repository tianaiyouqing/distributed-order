package cloud.tianai.order.core.common.wrapper;

import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 16:46
 * @Description: 基本订单数据包装器
 */
@Data
@NoArgsConstructor
public class BasicOrderWrapper implements OrderWrapper {

    private OrderMasterDO orderMasterDO;
    private Collection<OrderDetailDO> orderDetailDOS;

    public BasicOrderWrapper(OrderMasterDO orderMasterDO, Collection<OrderDetailDO> orderDetailDOS) {
        this.orderMasterDO = orderMasterDO;
        this.orderDetailDOS = orderDetailDOS;
    }


    public BasicOrderWrapper(OrderWrapper orderWrapper) {
        this.orderMasterDO = orderWrapper.getOrderMaster();
        this.orderDetailDOS = orderWrapper.getOrderDetail();
    }

    @Override
    public OrderMasterDO getOrderMaster() {
        return orderMasterDO;
    }

    @Override
    public Collection<OrderDetailDO> getOrderDetail() {
        return orderDetailDOS;
    }


    public void setOrderMaster(OrderMasterDO orderMaster) {
        this.orderMasterDO = orderMaster;
    }

    public void setOrderDetail(Collection<OrderDetailDO> orderDetail) {
        this.orderDetailDOS = orderDetail;
    }
}
