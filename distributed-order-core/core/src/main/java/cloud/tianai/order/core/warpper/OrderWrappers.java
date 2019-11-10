package cloud.tianai.order.core.warpper;

import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.core.common.wrapper.BasicOrderWrapper;
import cloud.tianai.order.core.warpper.impl.DataGeneratorOrderWrapper;
import cloud.tianai.order.id.OrderIdGenerator;

import java.util.Collection;

public class OrderWrappers {

    public static OrderWrapper wrap(OrderIdGenerator orderIdGenerator, OrderSaveForm orderSaveForm) {
        return new DataGeneratorOrderWrapper(orderIdGenerator, orderSaveForm);
    }

    public static OrderWrapper wrap(OrderMasterDO orderMasterDO, Collection<OrderDetailDO> orderDetailDOS) {
        return new BasicOrderWrapper(orderMasterDO, orderDetailDOS);
    }

}
