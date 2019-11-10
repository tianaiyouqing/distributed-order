package cloud.tianai.order.core.pay.util;

import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import cloud.tianai.order.core.sdk.dto.*;
import cloud.tianai.order.core.api.pay.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.info.OrderAddressInfo;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderDataConverter {

    public static OrderCreateResult orderWrapper2OrderCreateResult(OrderWrapper orderWrapper) {
        OrderCreateResult orderCreateResult = new OrderCreateResult();
        orderCreateResult.setOid(orderWrapper.getOrderMaster().getOid());
        orderCreateResult.setUid(orderWrapper.getOrderMaster().getUid());
        orderCreateResult.setBid(orderWrapper.getOrderMaster().getBid());
        orderCreateResult.setCreateTime(orderWrapper.getOrderMaster().getCreateTime());
        orderCreateResult.setOrderAmount(orderWrapper.getOrderMaster().getOrderAmount());
        orderCreateResult.setCouponPrice(orderWrapper.getOrderMaster().getDiscountFee());
        Collection<OrderDetailDO> orderDetail = orderWrapper.getOrderDetail();
        Collection<SimpleOrderProductDTO> productDTOS = new ArrayList<>(orderDetail.size());
        for (OrderDetailDO orderDetailDO : orderDetail) {
            SimpleOrderProductDTO simpleOrderProductDTO = new SimpleOrderProductDTO();
            simpleOrderProductDTO.setNum(orderDetailDO.getProductQuantity());
            simpleOrderProductDTO.setSkuId(orderDetailDO.getSkuId());
            simpleOrderProductDTO.setSku(orderDetailDO.getSku());
            simpleOrderProductDTO.setSpuId(orderDetailDO.getSpuId());
            productDTOS.add(simpleOrderProductDTO);
        }
        orderCreateResult.setProductDTOS(productDTOS);
        return orderCreateResult;
    }


}
