package cloud.tianai.order.core.warpper;

import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.warpper.impl.BasicOrderWrapper;
import cloud.tianai.order.core.warpper.impl.DataGeneratorOrderWrapper;
import cloud.tianai.order.core.id.OrderIdGenerator;

import java.util.Collection;

public class OrderWrappers {

    public static OrderWrapper wrap(OrderIdGenerator orderIdGenerator, OrderSaveForm orderSaveForm) {
        return new DataGeneratorOrderWrapper(orderIdGenerator, orderSaveForm);
    }

    public static OrderWrapper wrap(OrderMasterDO orderMasterDO, Collection<OrderDetailDO> orderDetailDOS) {
        return new BasicOrderWrapper(orderMasterDO, orderDetailDOS);
    }
}
