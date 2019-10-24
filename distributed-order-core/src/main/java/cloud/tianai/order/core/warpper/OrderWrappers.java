package cloud.tianai.order.core.warpper;

import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.warpper.impl.BasicOrderWrapper;
import cloud.tianai.order.core.id.OrderIdGenerator;

public class OrderWrappers {

    public static OrderWrapper wrap(OrderIdGenerator orderIdGenerator, OrderSaveForm orderSaveForm) {
        return new BasicOrderWrapper(orderIdGenerator, orderSaveForm);
    }

}
