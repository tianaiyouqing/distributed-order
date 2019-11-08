package cloud.tianai.order.core.common.wrapper;

import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;

import java.io.Serializable;
import java.util.Collection;

public interface OrderWrapper extends Serializable {

    /**
     * 获取订单主信息
     * @return 订单主信息
     */
    OrderMasterDO getOrderMaster();

    /**
     * 获取订单详情信息
     * @return 订单详情信息
     */
    Collection<OrderDetailDO> getOrderDetail();
}
