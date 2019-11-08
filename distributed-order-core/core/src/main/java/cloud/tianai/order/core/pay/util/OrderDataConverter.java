package cloud.tianai.order.core.pay.util;

import cloud.tianai.order.core.api.basic.form.OrderSaveForm;
import cloud.tianai.order.core.common.dto.OrderCreateParamDTO;
import cloud.tianai.order.core.common.dto.OrderCreateResult;
import cloud.tianai.order.core.common.dto.ProductSkuInfoDTO;
import cloud.tianai.order.core.common.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.api.basic.information.AddressInfo;
import cloud.tianai.order.core.api.basic.information.BasicBusinessInfo;
import cloud.tianai.order.core.api.basic.information.BasicUserInfo;
import cloud.tianai.order.core.api.basic.information.ProductInfo;
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
        orderCreateResult.setCouponPrice(orderWrapper.getOrderMaster().getCouponPrice());
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


    public static OrderSaveForm orderCreateParamDTO2OrderSaveForm(OrderCreateParamDTO paramDTO) {
        OrderSaveForm orderSaveForm = new OrderSaveForm();
        orderSaveForm.setPayRemark(paramDTO.getPayRemark());
        orderSaveForm.setCouponPrice(paramDTO.getCouponPrice());
        orderSaveForm.setCustomCouponPrice(paramDTO.getCustomCouponPrice());
        orderSaveForm.setCouponId(paramDTO.getCouponId());
        orderSaveForm.setPlatformType(paramDTO.getPlatformType());
        orderSaveForm.setChannelId(paramDTO.getChannelId());
        orderSaveForm.setPlatformId(paramDTO.getPlatformId());
        orderSaveForm.setOrderType(paramDTO.getOrderType());

        BasicUserInfo userInfo = new BasicUserInfo();
        userInfo.setUid(paramDTO.getUid());

        orderSaveForm.setUserInfo(userInfo);

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setAddressDesc(paramDTO.getAddressDesc());
        addressInfo.setArea(paramDTO.getArea());
        addressInfo.setBuyerName(paramDTO.getBuyerName());
        addressInfo.setBuyerPhone(paramDTO.getBuyerPhone());
        addressInfo.setCity(paramDTO.getCity());
        addressInfo.setProvince(paramDTO.getProvince());
        addressInfo.setStreet(paramDTO.getStreet());

        orderSaveForm.setAddressInfo(addressInfo);

        BasicBusinessInfo basicBusinessInfo = new BasicBusinessInfo();
        basicBusinessInfo.setBid(paramDTO.getSkuInfoDTOS().iterator().next().getBid());

        orderSaveForm.setBusinessInfo(basicBusinessInfo);
        Collection<ProductInfo> productInfos = new ArrayList<>(paramDTO.getSkuInfoDTOS().size());
        for (ProductSkuInfoDTO skuInfoDTO : paramDTO.getSkuInfoDTOS()) {
            ProductInfo productInfo = productSkuInfoDTO2ProductInfo(skuInfoDTO, paramDTO.getProductDTOS());
            productInfos.add(productInfo);
        }

        orderSaveForm.setProductInfos(productInfos);

        return orderSaveForm;
    }


    public static ProductInfo productSkuInfoDTO2ProductInfo(ProductSkuInfoDTO skuInfoDTO, Collection<SimpleOrderProductDTO> productDTOS) {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setSpuId(skuInfoDTO.getSpuId());
        productInfo.setBid(skuInfoDTO.getBid());
        productInfo.setProductName(skuInfoDTO.getProductName());
        productInfo.setProductPrice(skuInfoDTO.getProductPrice());
        productInfo.setProductIcon(skuInfoDTO.getProductIcon());
        productInfo.setSku(skuInfoDTO.getSku());
        productInfo.setSkuId(skuInfoDTO.getSkuId());
        productInfo.setSkuDesc(skuInfoDTO.getSkuStr());
        productInfo.setProductBarcode(skuInfoDTO.getBarcode());
        List<SimpleOrderProductDTO> collect = productDTOS.stream().filter(pd -> pd.getSpuId().equals(skuInfoDTO.getSpuId())).collect(Collectors.toList());
        for (SimpleOrderProductDTO simpleOrderProductDTO : collect) {
            if(!Objects.isNull(productInfo.getSkuId())) {
                if(productInfo.getSkuId().equals(simpleOrderProductDTO.getSkuId())) {
                    productInfo.setProductQuantity(simpleOrderProductDTO.getNum());
                    break;
                }
            }else {
                if(productInfo.getSku().equals(simpleOrderProductDTO.getSku())) {
                    productInfo.setProductQuantity(simpleOrderProductDTO.getNum());
                    break;
                }
            }
        }
        return productInfo;
    }
}
