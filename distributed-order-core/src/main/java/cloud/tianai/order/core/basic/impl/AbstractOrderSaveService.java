package cloud.tianai.order.core.basic.impl;

import cloud.tianai.order.core.basic.OrderSaveService;
import cloud.tianai.order.core.basic.event.PostOrderInsertEvent;
import cloud.tianai.order.core.basic.event.PreOrderInsertEvent;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.exception.OrderSaveException;
import cloud.tianai.order.core.id.OrderIdGenerator;
import cloud.tianai.order.core.util.OrderCheckUtils;
import cloud.tianai.order.core.warpper.OrderWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import tianai.cloud.order.api.response.ApiResponse;
import tianai.cloud.order.api.response.ApiResponseStatusEnum;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 9:20
 * @Description: 抽象的订单保存业务
 */
@Slf4j
public abstract class AbstractOrderSaveService implements OrderSaveService {

    /**
     * 这里直接使用@Transactional注解即可， 逻辑是下的单只会下到一个库中，所以只需要使用对应库的事物即可， 这点shardingJdbc已经实现了
     *
     * @param orderSaveForm 订单添加所需要的参数
     * @return OrderMasterAndOrderDetail
     * @throws OrderSaveException 添加失败抛出异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderWrapper insertOrder(OrderSaveForm orderSaveForm) throws OrderSaveException {
        // 校验参数
        ApiResponse<?> checkResult = OrderCheckUtils.checkOrderSaveForm(orderSaveForm);
        if (!ApiResponseStatusEnum.SUCCESS.getCode().equals(checkResult.getCode())) {
            throw new OrderSaveException(checkResult.getMsg());
        }
        // 订单包装
        OrderWrapper orderWrapper = wrapSaveOrder(getOrderIdGenerator(), orderSaveForm);

        orderWrapper = postProcessBeforeInsertOrder(orderWrapper);

        // 存储到数据库中
        OrderWrapper result = null;
        try {
            result = doInsertOrder(orderWrapper);
        } catch (Exception e) {
            log.error("插入订单失败， param={}, wrapper={} e={}", orderSaveForm, orderWrapper, e);
            throw new OrderSaveException("插入订单失败", e);
        }

        result = postProcessAfterInsertOrder(result);

        return result;
    }



    OrderWrapper postProcessAfterInsertOrder(OrderWrapper orderWrapper) throws OrderSaveException {
        getApplicationContext().publishEvent(new PostOrderInsertEvent(orderWrapper));
        return orderWrapper;
    }

    OrderWrapper postProcessBeforeInsertOrder(OrderWrapper orderWrapper) throws OrderSaveException {
        getApplicationContext().publishEvent(new PreOrderInsertEvent(orderWrapper));
        return orderWrapper;
    }


    // =======================================
    // 抽象方法
    // =======================================

    /**
     * 包装保存订单的信息
     *
     * @param orderIdGenerator 订单ID生成器
     * @param orderSaveForm    订单报错form
     * @return
     */
    protected abstract OrderWrapper wrapSaveOrder(OrderIdGenerator orderIdGenerator, OrderSaveForm orderSaveForm);

    /**
     * 存储订单
     * @param orderWrapper 订单数据包装器
     * @return OrderWrapper
     * @throws OrderSaveException 插入失败异常
     */
    protected abstract OrderWrapper doInsertOrder(OrderWrapper orderWrapper) throws OrderSaveException;

    protected abstract OrderIdGenerator getOrderIdGenerator();

    protected abstract ApplicationContext getApplicationContext();

}
