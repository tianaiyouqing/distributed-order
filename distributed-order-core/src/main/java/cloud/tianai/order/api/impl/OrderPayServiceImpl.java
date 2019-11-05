package cloud.tianai.order.api.impl;


import cloud.tianai.order.api.interceptor.OrderCreateInterceptor;
import cloud.tianai.order.api.util.OrderDataConverter;
import cloud.tianai.order.api.util.OrderParamCheckUtils;
import cloud.tianai.order.core.basic.OrderSaveService;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.exception.OrderSaveException;
import cloud.tianai.order.core.interceptor.OrderInterceptorChain;
import cloud.tianai.order.core.interceptor.OrderInterceptorChainHolder;
import cloud.tianai.order.core.warpper.OrderWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tianai.cloud.order.api.OrderPayService;
import tianai.cloud.order.api.ProductCouponService;
import tianai.cloud.order.api.ProductInfoService;
import tianai.cloud.order.api.ProductStockService;
import tianai.cloud.order.api.dto.*;
import tianai.cloud.order.api.exception.OrderCreateException;
import tianai.cloud.order.api.response.ApiResponse;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/5 17:09
 * @Description: 下单业务层
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderPayServiceImpl implements OrderPayService {

    private final ProductInfoService productInfoService;
    private final ProductCouponService productCouponService;
    private final ProductStockService productStockService;
    private final OrderSaveService orderSaveService;

    @Override
    public ApiResponse<OrderCreateResult> createOrder(OrderCreateParamDTO paramDTO) {
       return createOrder(paramDTO, true, true, true);
    }

    public ApiResponse<OrderCreateResult> createOrder(OrderCreateParamDTO paramDTO,
                                                      Boolean usedCoupon,
                                                      Boolean autoWarpProduct,
                                                      Boolean reduceStock
    ) {
        //1. 校验参数
        ApiResponse checkResult = OrderParamCheckUtils.checkNecessaryParam(paramDTO);
        if (!checkResult.isSuccess()) {
            // 如果参数校验失败，则世界把错误信息返回
            return checkResult;
        }
        // 1.2 执行拦截器
        OrderInterceptorChain<OrderCreateParamDTO, OrderCreateResult> chain = getInterceptorChain();
        ApiResponse<OrderCreateResult> intercept = chain.intercept(paramDTO);
        if(intercept.isSuccess()) {
            // 拦截成功
            return intercept;
        }

        //2. 包装商品信息
        if (autoWarpProduct) {
            try {
                warpProductInfo(paramDTO);
            } catch (IllegalArgumentException e) {
                return ApiResponse.ofError(e.getMessage());
            }
        }


        //3. 扣库存
        ApiResponse<String> stockResult = null;
        if (reduceStock) {
            stockResult = reduceStock(paramDTO.getProductDTOS());
            if (!stockResult.isSuccess()) {
                // 扣库存失败
                log.error("库存扣除失败, param={}, 返回的错误信息={}", paramDTO.getProductDTOS(), stockResult.getMsg());
                return ApiResponse.ofError("商品库存不足");
            }
        }
        //4. 使用优惠券
        ApiResponse<String> finalStockResult = stockResult;
        ProductCouponResult productCouponResult = warpCoupon(paramDTO, usedCoupon, (e) -> {
            // 尽量回滚库存
            rollback(paramDTO, null, finalStockResult);
            throw new OrderCreateException(e);
        });
        try {
            //5. 下单
            OrderCreateResult orderCreateResult = doCreateOrder(paramDTO);
            //6. 确认库存
            commitStock(stockResult);
            return ApiResponse.ofSuccess(orderCreateResult);
        } catch (OrderSaveException e) {
            //7. 下单失败
            rollback(paramDTO, productCouponResult, stockResult);
            throw e;
        } catch (Exception e) {
            // 下单失败
            rollback(paramDTO, productCouponResult, stockResult);
            throw new OrderCreateException(e);
        }
    }

    private OrderInterceptorChain<OrderCreateParamDTO, OrderCreateResult> getInterceptorChain() {
        OrderInterceptorChain<OrderCreateParamDTO, OrderCreateResult> result =
                OrderInterceptorChainHolder
                .<OrderCreateParamDTO, OrderCreateResult>getChain(OrderCreateInterceptor.class);
        return result;
    }

    protected OrderCreateResult doCreateOrder(OrderCreateParamDTO paramDTO) throws OrderSaveException {
        OrderSaveForm orderSaveForm = warpOrderSaveForm(paramDTO);
        OrderWrapper orderWrapper = orderSaveService.insertOrder(orderSaveForm);
        OrderCreateResult orderCreateResult = OrderDataConverter.orderWrapper2OrderCreateResult(orderWrapper);
        return orderCreateResult;
    }

    private OrderSaveForm warpOrderSaveForm(OrderCreateParamDTO paramDTO) {
        OrderSaveForm orderSaveForm = OrderDataConverter.orderCreateParamDTO2OrderSaveForm(paramDTO);
        return orderSaveForm;
    }

    private void commitStock(ApiResponse<String> stockResult) {
        if(Objects.nonNull(stockResult)) {
            productStockService.commit(stockResult.getData());
        }
    }

    private void rollback(OrderCreateParamDTO paramDTO,
                          ProductCouponResult productCouponResult, ApiResponse<String> stockResult) {
        // 尽量回滚优惠券信息
        if (!Objects.isNull(productCouponResult)) {
            productCouponService.rollback(productCouponResult);
        }
        // 尽量回滚库存
        if (!Objects.isNull(stockResult)) {
            productStockService.rollback(stockResult.getData());
        }

    }

    public ApiResponse<String> reduceStock(Collection<SimpleOrderProductDTO> productDTOS) {
        try {
            ApiResponse<String> stockResult = productStockService.stock(productDTOS);
            return stockResult;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.ofError(e.getMessage());
        }
    }

    public ProductCouponResult warpCoupon(OrderCreateParamDTO paramDTO,
                                          Boolean usedCoupon,
                                          Function<Exception, ProductCouponResult> exceptionCallback) {

        Long customCouponPrice = 0L;
        if (paramDTO.getCustomCouponPrice() != null && paramDTO.getCustomCouponPrice() > 0) {
            customCouponPrice = paramDTO.getCustomCouponPrice();
        }
        if(!usedCoupon) {
            // 不使用优惠券
            paramDTO.setCouponPrice(customCouponPrice);
            return null;
        }

        ProductCouponResult productCouponResult = null;
        try {
            productCouponResult = productCouponService.useCoupon(paramDTO);
        } catch (Exception e) {
            // 如果出现异常， 进行回调
            return exceptionCallback.apply(e);
        }
        Long otterCouponPrice = 0L;
        if (productCouponResult.getOtterCouponPrice() != null && productCouponResult.getOtterCouponPrice() > 0) {
            otterCouponPrice = productCouponResult.getOtterCouponPrice();
        }

        Map<ProductSkuInfoDTO, Long> couponPriceMap = productCouponResult.getCouponPrice();
        if (CollectionUtils.isEmpty(couponPriceMap)) {
            paramDTO.setCouponPrice(customCouponPrice + otterCouponPrice);
        } else {
            // 取优惠中心的优惠价格
            Long reduce = couponPriceMap.values().stream().reduce(0L, (a, b) -> a + b);
            paramDTO.setCouponPrice(customCouponPrice + reduce);
        }

        return productCouponResult;
    }

    public void warpProductInfo(OrderCreateParamDTO paramDTO) {
        Collection<SimpleOrderProductDTO> productDTOS = paramDTO.getProductDTOS();
        List<ProductInfoService.ProductSkuSearchParam> productSkuParam = productDTOS
                .stream()
                .map(p -> new ProductInfoService.ProductSkuSearchParam(p.getSpuId(), p.getSkuId(), p.getSku()))
                .collect(Collectors.toList());
        //1. 查询商品信息
        Collection<ProductSkuInfoDTO> productInfos = productInfoService.listProductSku(productSkuParam);
        //2. 匹配查询到的商品信息和预计要查询的商品是否一致
        Map<String, List<ProductSkuInfoDTO>> productSkuInfoMap = productInfos
                .stream()
                .collect(Collectors.groupingBy(ProductSkuInfoDTO::getSpuId));
        for (SimpleOrderProductDTO productDTO : productDTOS) {
            List<ProductSkuInfoDTO> productInfo = productSkuInfoMap.get(productDTO.getSpuId());
            if (CollectionUtils.isEmpty(productInfo)) {
                throw new IllegalArgumentException("未查询到对应的商品信息[ " + productDTO.getSpuId() + "]");
            }
            Optional<ProductSkuInfoDTO> any = productInfo
                    .stream().filter(p -> p.getSkuId().equals(productDTO.getSkuId()) || p.getSkuId().equals(productDTO.getSku()))
                    .findAny();
            if (!any.isPresent()) {
                throw new IllegalArgumentException("未查询到对应的商品信息[ "
                        + productDTO.getSpuId()
                        + ": "
                        + productDTO.getSkuId()
                        + ":"
                        + productDTO.getSku()
                        + "]");
            }
        }
        //3. 判断这些商品信息是否是同一个店铺的， 如果不是则不能下单
        Map<String, List<ProductSkuInfoDTO>> productSkuInfoGroupByBid = productInfos
                .stream()
                .collect(Collectors.groupingBy(ProductSkuInfoDTO::getBid));
        if (productSkuInfoGroupByBid.size() > 1) {
            throw new IllegalArgumentException("下单的商品信息只能是一个店铺的商品");
        }
        //4. 填充商品Sku信息
        paramDTO.setSkuInfoDTOS(productInfos);
    }
}
