package cloud.tianai.order.core.warpper.impl;

import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.core.common.enums.AfterSalesStatusEnum;
import cloud.tianai.order.core.common.enums.OrderStatusEnum;
import cloud.tianai.order.core.common.enums.OrderTypeEnum;
import cloud.tianai.order.core.common.info.OrderAddressInfo;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.core.sdk.dto.BasicBusinessInfoDTO;
import cloud.tianai.order.core.sdk.dto.BasicUserInfoDTO;
import cloud.tianai.order.core.sdk.dto.ProductDTO;
import cloud.tianai.order.core.sdk.dto.PromotionInfoDTO;
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
        BasicUserInfoDTO userInfo = orderSaveForm.getUserInfo();
        BasicBusinessInfoDTO businessInfo = orderSaveForm.getBusinessInfo();
        OrderAddressInfo orderAddressInfo = orderSaveForm.getOrderAddressInfo();
        Collection<ProductDTO> productDTOS = orderSaveForm.getProductDTOS();
        String oid = orderIdGenerator.generatorOrderId(userInfo.getUid(), businessInfo.getBid());
        orderMasterDO = new OrderMasterDO();
        orderDetailDOS = new ArrayList<>(productDTOS.size());

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
        // 优惠信息
        PromotionInfoDTO promotionInfoDTO = orderSaveForm.getPromotionInfoDTO();
        if(Objects.nonNull(promotionInfoDTO)) {
            // 优惠券ID
            orderMasterDO.setPromotionId(promotionInfoDTO.getPromotionId());
            // 优惠价格
            Long discountFee = promotionInfoDTO.getDiscountFee();
            if(discountFee == null || discountFee < 0) {
                discountFee = 0L;
            }
            // 设置优惠价格
            orderMasterDO.setDiscountFee(discountFee);
            // 设置优惠信息的名称
            orderMasterDO.setPromotionName(promotionInfoDTO.getPromotionName());
        }else {
            // 设置优惠价格
            orderMasterDO.setDiscountFee(0L);
        }
        Long customDiscountFee = orderSaveForm.getCustomDiscountFee();

        if( customDiscountFee != null && customDiscountFee > 0 ) {
            orderMasterDO.setCustomDiscountFee(customDiscountFee);
            // 优惠价格再加上自定义优惠价格
            orderMasterDO.setDiscountFee(orderMasterDO.getDiscountFee() + customDiscountFee);
        }
        // 总优惠价格
        Long discountFee = orderMasterDO.getDiscountFee();

        Long orderAmount = 0L;
        String prevOrderDetailId = null;
        for (ProductDTO productDTO : productDTOS) {
            // 生成订单详情ID
            String orderDetailId = orderIdGenerator.generatorOrderDetailId(prevOrderDetailId, oid);
            OrderDetailDO orderDetailDO = new OrderDetailDO();
            orderDetailDO.setOrderDetailId(orderDetailId);
            orderDetailDO.setOid(oid);
            orderDetailDO.setSpuId(productDTO.getSpuId());
            orderDetailDO.setSkuId(productDTO.getSkuId());
            orderDetailDO.setSku(productDTO.getSku());
            orderDetailDO.setSkuDesc(productDTO.getSkuDesc());
            orderDetailDO.setProductName(productDTO.getProductName());
            orderDetailDO.setProductPrice(productDTO.getProductPrice());
            orderDetailDO.setProductQuantity(productDTO.getProductQuantity());
            orderDetailDO.setProductIcon(productDTO.getProductIcon());
            orderDetailDO.setProductBarcode(productDTO.getProductBarcode());
            // 记录总价格
            orderAmount += orderDetailDO.getProductPrice() * orderDetailDO.getProductQuantity();
            orderDetailDOS.add(orderDetailDO);
            prevOrderDetailId = orderDetailId;
        }
        // 总价减去优惠价格
        orderAmount -= discountFee;
        if(orderAmount < 0L) {
            orderAmount = 0L;
        }
        // 订单类型
        Integer orderType = orderSaveForm.getOrderType();
        if(Objects.isNull(orderType)) {
            orderType = OrderTypeEnum.CASH_ON_DELIVERY.getCode();
        }
        orderMasterDO.setOrderType(orderType);
        // 订单总价
        orderMasterDO.setOrderAmount(orderAmount);
        // 买家留言
        orderMasterDO.setPayRemark(orderSaveForm.getPayRemark());
        // 买家名称
        orderMasterDO.setBuyerName(orderAddressInfo.getBuyerName());
        // 买家手机
        orderMasterDO.setBuyerPhone(orderAddressInfo.getBuyerPhone());
        // 地址相关
        orderMasterDO.setProvince(orderAddressInfo.getProvince());
        orderMasterDO.setCity(orderAddressInfo.getCity());
        orderMasterDO.setArea(orderAddressInfo.getArea());
        orderMasterDO.setStreet(orderAddressInfo.getStreet());
        orderMasterDO.setAddressDesc(orderAddressInfo.getAddressDesc());
        // 下单时间
        if(orderSaveForm.getOrderCreateTime() != null) {
            orderMasterDO.setCreateTime(orderSaveForm.getOrderCreateTime());
        }
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
