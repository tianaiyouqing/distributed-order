package cloud.tianai.order.core.warpper;

import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;

import java.util.Collection;

public interface OrderWrapper {

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
