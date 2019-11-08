package cloud.tianai.order.core.warpper.impl;

import cloud.tianai.order.core.api.basic.form.OrderSaveForm;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.core.api.basic.enums.AfterSalesStatusEnum;
import cloud.tianai.order.core.api.basic.enums.OrderStatusEnum;
import cloud.tianai.order.core.api.basic.enums.OrderTypeEnum;
import cloud.tianai.order.core.api.basic.information.AddressInfo;
import cloud.tianai.order.core.api.basic.information.BasicBusinessInfo;
import cloud.tianai.order.core.api.basic.information.BasicUserInfo;
import cloud.tianai.order.core.api.basic.information.ProductInfo;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.id.OrderIdGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 22:43
 * @Description: 订单数据生成包装器
 */
public class DataGeneratorOrderWrapper implements OrderWrapper {

    private OrderIdGenerator orderIdGenerator;
    private OrderSaveForm orderSaveForm;

    private OrderMasterDO orderMasterDO;
    private Collection<OrderDetailDO> orderDetailDOS;


    public DataGeneratorOrderWrapper(OrderIdGenerator orderIdGenerator, OrderSaveForm orderSaveForm) {
        this.orderIdGenerator = orderIdGenerator;
        this.orderSaveForm = orderSaveForm;

        warp();
    }

    private void warp() {
        // 生成订单号
        BasicUserInfo userInfo = orderSaveForm.getUserInfo();
        BasicBusinessInfo businessInfo = orderSaveForm.getBusinessInfo();
        AddressInfo addressInfo = orderSaveForm.getAddressInfo();
        Collection<ProductInfo> productInfos = orderSaveForm.getProductInfos();
        String oid = orderIdGenerator.generatorOrderId(userInfo.getUid(), businessInfo.getBid());
        orderMasterDO = new OrderMasterDO();
        orderDetailDOS = new ArrayList<>(productInfos.size());

        // 订单ID
        orderMasterDO.setOid(oid);
        // 用户ID
        orderMasterDO.setUid(userInfo.getUid());
        // 商户ID
        orderMasterDO.setBid(businessInfo.getBid());
        // 渠道ID
        orderMasterDO.setChannelId(orderSaveForm.getChannelId());
        // 平台ID
        orderMasterDO.setPlatformId(orderSaveForm.getPlatformId());
        // 外部订单ID
        orderMasterDO.setExternalOrderId(orderSaveForm.getExternalOrderId());
        // 订单状态
        orderMasterDO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        // 售后状态
        orderMasterDO.setAfterSalesStatus(AfterSalesStatusEnum.DEFAULT.getCode());
        // 平台类型
        orderMasterDO.setPlatformType(orderSaveForm.getPlatformType());
        // 优惠券ID
        orderMasterDO.setCouponId(orderSaveForm.getCouponId());
        // 自定义优惠价格
        Long customCouponPrice = orderSaveForm.getCustomCouponPrice();
        orderMasterDO.setCustomCouponPrice(customCouponPrice);

        Long orderAmount = 0L;
        String prevOrderDetailId = null;
        for (ProductInfo productInfo : productInfos) {
            // 生成订单详情ID
            String orderDetailId = orderIdGenerator.generatorOrderDetailId(prevOrderDetailId, oid);
            OrderDetailDO orderDetailDO = new OrderDetailDO();
            orderDetailDO.setOrderDetailId(orderDetailId);
            orderDetailDO.setOid(oid);
            orderDetailDO.setSpuId(productInfo.getSpuId());
            orderDetailDO.setSkuId(productInfo.getSkuId());
            orderDetailDO.setSku(productInfo.getSku());
            orderDetailDO.setSkuDesc(productInfo.getSkuDesc());
            orderDetailDO.setProductName(productInfo.getProductName());
            orderDetailDO.setProductPrice(productInfo.getProductPrice());
            orderDetailDO.setProductQuantity(productInfo.getProductQuantity());
            orderDetailDO.setProductIcon(productInfo.getProductIcon());
            orderDetailDO.setProductBarcode(productInfo.getProductBarcode());
            // 记录总价格
            orderAmount += orderDetailDO.getProductPrice() * orderDetailDO.getProductQuantity();
            orderDetailDOS.add(orderDetailDO);
            prevOrderDetailId = orderDetailId;
        }
        // 总价减去优惠价格
        if(Objects.nonNull(orderSaveForm.getCouponPrice()) && orderSaveForm.getCouponPrice() > 0) {
            orderAmount -= orderSaveForm.getCouponPrice();
            if(orderAmount < 0L) {
                orderAmount = 0L;
            }
        }
        // 订单类型
        Integer orderType = orderSaveForm.getOrderType();
        if(Objects.isNull(orderType)) {
            orderType = OrderTypeEnum.CASH_ON_DELIVERY.getCode();
        }
        orderMasterDO.setOrderType(orderType);
        // 订单总价
        orderMasterDO.setOrderAmount(orderAmount);
        // 优惠价格
        orderMasterDO.setCouponPrice(orderSaveForm.getCouponPrice());
        // 买家留言
        orderMasterDO.setPayRemark(orderSaveForm.getPayRemark());
        // 买家名称
        orderMasterDO.setBuyerName(addressInfo.getBuyerName());
        // 买家手机
        orderMasterDO.setBuyerPhone(addressInfo.getBuyerPhone());
        // 地址相关
        orderMasterDO.setProvince(addressInfo.getProvince());
        orderMasterDO.setCity(addressInfo.getCity());
        orderMasterDO.setArea(addressInfo.getArea());
        orderMasterDO.setStreet(addressInfo.getStreet());
        orderMasterDO.setAddressDesc(addressInfo.getAddressDesc());
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
