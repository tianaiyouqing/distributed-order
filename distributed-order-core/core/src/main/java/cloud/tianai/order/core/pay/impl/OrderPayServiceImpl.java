package cloud.tianai.order.core.pay.impl;


import cloud.tianai.order.common.exception.OrderException;
import cloud.tianai.order.core.pay.interceptor.OrderCreateInterceptor;
import cloud.tianai.order.core.pay.interceptor.OrderCreateInterceptorHolder;
import cloud.tianai.order.core.pay.util.OrderDataConverter;
import cloud.tianai.order.core.pay.util.OrderParamCheckUtils;
import cloud.tianai.order.common.interceptor.OrderInterceptorChain;
import cloud.tianai.order.common.response.ApiResponse;
import cloud.tianai.order.core.api.pay.OrderPayService;
import cloud.tianai.order.core.api.pay.dto.OrderCreateParam;
import cloud.tianai.order.core.api.pay.dto.OrderCreateResult;
import cloud.tianai.order.core.api.pay.exception.OrderCreateException;
import cloud.tianai.order.core.basic.BasicOrderSaveService;
import cloud.tianai.order.core.basic.form.OrderSaveForm;
import cloud.tianai.order.core.basic.exception.OrderSaveException;
import cloud.tianai.order.core.holder.OrderInterceptorChainHolder;
import cloud.tianai.order.core.sdk.PromotionService;
import cloud.tianai.order.core.sdk.ProductInfoService;
import cloud.tianai.order.core.sdk.ProductStockService;
import cloud.tianai.order.core.sdk.UserService;
import cloud.tianai.order.core.sdk.dto.*;
import cloud.tianai.order.core.api.pay.dto.SimpleOrderProductDTO;
import cloud.tianai.order.core.common.wrapper.OrderWrapper;
import cloud.tianai.order.core.util.ProductUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
@org.apache.dubbo.config.annotation.Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderPayServiceImpl implements OrderPayService {

    private final ProductInfoService productInfoService;
    private final PromotionService promotionService;
    private final ProductStockService productStockService;
    private final BasicOrderSaveService basicOrderSaveService;
    private final UserService userService;
    private final OrderCreateInterceptorHolder orderCreateInterceptorHolder;
    @Override
    public ApiResponse<OrderCreateResult> createOrder(OrderCreateParam paramDTO) {
        return createOrder(paramDTO, true, true);
    }

    public ApiResponse<OrderCreateResult> createOrder(OrderCreateParam paramDTO,
                                                      Boolean usedCoupon,
                                                      Boolean reduceStock
    ) {
        //1. 校验参数
        ApiResponse checkResult = OrderParamCheckUtils.checkNecessaryParam(paramDTO);
        if (!checkResult.isSuccess()) {
            // 如果参数校验失败，把错误信息返回
            return checkResult;
        }
        ApiResponse<OrderCreateResult> interceptorRes;
        // 执行硬编码拦截器， 这个拦截器主要做拦截黑名单用户，拦截IP地址等等能直接通过下单参数拦截的
        interceptorRes = applyOrderCreateInterceptorBeforeCreateOrder(paramDTO);
        if(!interceptorRes.isSuccess()) {
            // 拦截后把信息返回
            return interceptorRes;
        }

        // 包装商品信息
        Collection<ProductDTO> productDTOS = warpProductInfoOfThrow(paramDTO);
        // 获取用户信息
        BasicUserInfoDTO userInfoDTO = getBasicUserInfoOfThrow(paramDTO.getUid());
        // 获取商户信息
        BasicBusinessInfoDTO businessInfoDTO = getBasicBusinessInfoOfThrow(productDTOS.iterator().next().getBid());

        // 执行拦截器，在扣库存之前(这个拦截器一般用于拦截恶意订单，
        // 比如:恶意下单、黑名单用户、某商品不想出售、某店家已被列入黑名单 等等)
        interceptorRes = applyOrderCreateInterceptorBeforeReduceStock(paramDTO,
                productDTOS, userInfoDTO, businessInfoDTO);
        if(!interceptorRes.isSuccess()) {
            // 如果被拦截，直接把拦截信息返回
            return interceptorRes;
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
        PromotionInfoDTO promotionInfoDTO = null;
        if(usedCoupon) {
            promotionInfoDTO = useCoupon(paramDTO,
                    userInfoDTO,
                    businessInfoDTO,
                    productDTOS, (e) -> {
                // 尽量回滚库存
                rollback(paramDTO, null, finalStockResult);
                throw new OrderCreateException(e);
            });
        }
        try {
            //5. 下单
            OrderCreateResult orderCreateResult = doCreateOrder(paramDTO, productDTOS, userInfoDTO, businessInfoDTO, promotionInfoDTO);
            //6. 确认库存
            commitStock(stockResult);
            return ApiResponse.ofSuccess(orderCreateResult);
        } catch (OrderSaveException e) {
            //7. 下单失败
            rollback(paramDTO, promotionInfoDTO, stockResult);
            return ApiResponse.ofError(e.getMessage());
        } catch (Exception e) {
            // 下单失败
            rollback(paramDTO, promotionInfoDTO, stockResult);
            throw new OrderCreateException(e);
        }
    }

    /**
     * 应用在下单之前的拦截器
     * @param paramDTO 下单时的参数
     * @return
     */
    private ApiResponse<OrderCreateResult> applyOrderCreateInterceptorBeforeCreateOrder(OrderCreateParam paramDTO) {
        List<OrderCreateInterceptor> interceptors = orderCreateInterceptorHolder.getInterceptors();
        ApiResponse<OrderCreateResult> interceptorResponse;
        for (OrderCreateInterceptor interceptor : interceptors) {
            interceptorResponse = interceptor.beforeCreateOrder(paramDTO);
            if(!interceptorResponse.isSuccess()) {
                // 返回状态码不是200的话，视为拦截成功，直接把拦截信息返回
                return interceptorResponse;
            }
        }
        // 拦截失败，继续执行下面的逻辑
        return ApiResponse.ofSuccess(OrderCreateResult.EMPTY);
    }

    /**
     * 应用拦截器，在扣库存之前
     * @param paramDTO
     * @param productDTOS
     * @param userInfoDTO
     * @param businessInfoDTO
     * @return
     */
    private ApiResponse<OrderCreateResult> applyOrderCreateInterceptorBeforeReduceStock(OrderCreateParam paramDTO,
                                                                                        Collection<ProductDTO> productDTOS,
                                                                                        BasicUserInfoDTO userInfoDTO,
                                                                                        BasicBusinessInfoDTO businessInfoDTO) {
        List<OrderCreateInterceptor> interceptors = orderCreateInterceptorHolder.getInterceptors();
        ApiResponse<OrderCreateResult> interceptorResponse;
        for (OrderCreateInterceptor interceptor : interceptors) {
             interceptorResponse = interceptor.beforeReduceStock(productDTOS, userInfoDTO, businessInfoDTO, paramDTO);
             if(!interceptorResponse.isSuccess()) {
                 // 返回状态码不是200的话，视为拦截成功，直接把拦截信息返回
                 return interceptorResponse;
             }
        }
        // 拦截失败，继续执行下面的逻辑
        return ApiResponse.ofSuccess(OrderCreateResult.EMPTY);
    }

    private BasicBusinessInfoDTO getBasicBusinessInfoOfThrow(String bid) {
        ApiResponse<BasicBusinessInfoDTO> BusinessInfoResponse = userService.getBasicBusinessInfo(bid);
        if(BusinessInfoResponse.isSuccess()) {
            return BusinessInfoResponse.getData();
        }
        log.error("通过uid未找到用户信息，bid={}， sdkResponse={}", bid, BusinessInfoResponse);
        throw new OrderException("通过bid未找到商户信息");
    }

    private BasicUserInfoDTO getBasicUserInfoOfThrow(String uid) {
        ApiResponse<BasicUserInfoDTO> userInfoResponse = userService.getBasicUserInfo(uid);
        if(userInfoResponse.isSuccess()) {
            return userInfoResponse.getData();
        }
        log.error("通过uid未找到用户信息，uid={}， sdkResponse={}", uid, userInfoResponse);
        throw new OrderException("通过uid未找到用户信息");
    }

    protected OrderCreateResult doCreateOrder(OrderCreateParam paramDTO,
                                              Collection<ProductDTO> productDTOS,
                                              BasicUserInfoDTO userInfoDTO,
                                              BasicBusinessInfoDTO businessInfoDTO,
                                              PromotionInfoDTO promotionInfoDTO) throws OrderSaveException {
        OrderSaveForm orderSaveForm = warpOrderSaveForm(paramDTO, productDTOS, userInfoDTO, businessInfoDTO, promotionInfoDTO);
        OrderWrapper orderWrapper = basicOrderSaveService.insertOrder(orderSaveForm);
        OrderCreateResult orderCreateResult = OrderDataConverter.orderWrapper2OrderCreateResult(orderWrapper);
        return orderCreateResult;
    }

    private OrderSaveForm warpOrderSaveForm(OrderCreateParam paramDTO,
                                            Collection<ProductDTO> productDTOS,
                                            BasicUserInfoDTO userInfoDTO,
                                            BasicBusinessInfoDTO businessInfoDTO,
                                            PromotionInfoDTO promotionInfoDTO) {
        OrderSaveForm orderSaveForm = new OrderSaveForm();
        orderSaveForm.setPayRemark(paramDTO.getPayRemark());
        orderSaveForm.setPlatformType(paramDTO.getPlatformType());
        orderSaveForm.setChannelId(paramDTO.getChannelId());
        orderSaveForm.setPlatformId(paramDTO.getPlatformId());
        orderSaveForm.setOrderType(paramDTO.getOrderType());
        orderSaveForm.setUserInfo(userInfoDTO);
        orderSaveForm.setBusinessInfo(businessInfoDTO);
        orderSaveForm.setOrderAddressInfo(paramDTO.getAddress());
        orderSaveForm.setProductDTOS(productDTOS);
        // 设置优惠价格
        Long customDiscountFee = paramDTO.getCustomDiscountFee();
        customDiscountFee = (customDiscountFee == null || customDiscountFee < 0)? 0L : customDiscountFee;
        orderSaveForm.setPromotionInfoDTO(promotionInfoDTO);
        orderSaveForm.setCustomDiscountFee(customDiscountFee);
        return orderSaveForm;
    }

    private void commitStock(ApiResponse<String> stockResult) {
        if (Objects.nonNull(stockResult)) {
            productStockService.commit(stockResult.getData());
        }
    }

    private void rollback(OrderCreateParam paramDTO,
                          PromotionInfoDTO promotionInfoDTO, ApiResponse<String> stockResult) {
        // 尽量回滚优惠券信息
        if (!Objects.isNull(promotionInfoDTO)) {
            promotionService.rollback(promotionInfoDTO);
        }
        // 尽量回滚库存
        if (!Objects.isNull(stockResult)) {
            productStockService.rollback(stockResult.getData());
        }

    }

    public ApiResponse<String> reduceStock(Collection<SimpleOrderProductDTO> productDTOS) {
        try {
            Collection<ProductReduceStockDTO> productReduceStockDTOS = ProductUtils.warpProductReduceStock(productDTOS);
            ApiResponse<String> stockResult = productStockService.reduceStock(productReduceStockDTOS);
            return stockResult;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.ofError(e.getMessage());
        }
    }

    public PromotionInfoDTO useCoupon(OrderCreateParam paramDTO,
                                         BasicUserInfoDTO userInfoDTO,
                                         BasicBusinessInfoDTO businessInfoDTO,
                                         Collection<ProductDTO> productDTOS,
                                         Function<Exception, PromotionInfoDTO> exceptionCallback) {

        PromotionInfoDTO promotionInfoDTO = null;
        try {
            OrderPromotionParam promotionParam = OrderPromotionParam.builder()
                    .addressInfo(paramDTO.getAddress())
                    .userInfoDTO(userInfoDTO)
                    .businessInfoDTO(businessInfoDTO)
                    .productDTOS(productDTOS)
                    .promotionId(paramDTO.getPromotionId())
                    .build();
            promotionInfoDTO = promotionService.usePromotion(promotionParam);
        } catch (Exception e) {
            // 如果出现异常， 进行回调
            return exceptionCallback.apply(e);
        }

        return promotionInfoDTO;
    }

    public Collection<ProductDTO> warpProductInfoOfThrow(OrderCreateParam paramDTO) {
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
        return ProductUtils.warpProductDTO(productInfos, productDTOS);
    }
}
