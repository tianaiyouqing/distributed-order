package cloud.tianai.order.core.api.basic;

import cloud.tianai.order.core.api.basic.form.OrderSaveForm;
import cloud.tianai.order.core.api.basic.exception.OrderSaveException;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 18:55
 * @Description: 订单存储
 */
public interface BasicOrderSaveService {

    /**
     * 添加订单
     * @param orderSaveForm 订单添加所需要的参数
     * @return OrderWrapper
     * @throws OrderSaveException 保存失败抛出异常
     */
    OrderWrapper insertOrder(OrderSaveForm orderSaveForm) throws OrderSaveException;

}
