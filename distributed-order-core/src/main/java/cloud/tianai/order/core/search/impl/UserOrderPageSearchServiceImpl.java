package cloud.tianai.order.core.search.impl;

import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.dto.OrderMasterDTO;
import cloud.tianai.order.core.exception.OrderSearchException;
import cloud.tianai.order.core.exception.UserOrderSearchException;
import cloud.tianai.order.core.mapper.OrderDetailMapper;
import cloud.tianai.order.core.mapper.OrderMasterMapper;
import cloud.tianai.order.core.search.AbstractOrderPageSearchService;
import cloud.tianai.order.core.search.form.OrderSearchForm;
import cloud.tianai.order.core.util.OrderMergeUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 19:41
 * @Description: 用户订单分页搜索器
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserOrderPageSearchServiceImpl extends AbstractOrderPageSearchService {

    private final OrderMasterMapper orderMasterMapper;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    protected void checkParamOfThrow(OrderSearchForm search) throws OrderSearchException {
        if(Objects.isNull(search) || StringUtils.isBlank(search.getUid())) {
            throw new UserOrderSearchException("用户库搜索数据失败， 用户ID为空");
        }
    }

    @Override
    protected IPage<OrderMasterDTO> doListOrderForPage(OrderSearchForm search, Integer pageNum, Integer pageSize) {
        Wrapper<OrderMasterDO> queryWrapper = warpSearchParam(search);
        Page<OrderMasterDO> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        IPage<OrderMasterDO> searchResult = orderMasterMapper.selectPage(page, queryWrapper);

        IPage<OrderMasterDTO> result = converter(searchResult);
        return result;
    }

    private IPage<OrderMasterDTO> converter(IPage<OrderMasterDO> page) {
        Page<OrderMasterDTO> result = new Page<>();
        BeanUtils.copyProperties(page, result);

        List<OrderMasterDO> records = page.getRecords();

        List<OrderMasterDTO> collect = converter(records);
        result.setRecords(collect);
        return result;
    }
    private List<OrderMasterDTO> converter(List<OrderMasterDO> records) {
        List<OrderMasterDTO> collect = records.stream()
                .map(o -> OrderMergeUtils.merge(o, new OrderMasterDTO()))
                .collect(Collectors.toList());
        return collect;
    }
    private Wrapper<OrderMasterDO> warpSearchParam(OrderSearchForm search) {
        LambdaQueryWrapper<OrderMasterDO> lambdaQueryWrapper = Wrappers.<OrderMasterDO>lambdaQuery();
        String uid = search.getUid();
        lambdaQueryWrapper.eq(OrderMasterDO::getUid, uid);

        String oid = search.getOid();
        String bid = search.getBid();
        String channelId = search.getChannelId();
        String platformId = search.getPlatformId();
        String externalOrderId = search.getExternalOrderId();
        Integer orderStatus = search.getOrderStatus();
        Integer afterSalesStatus = search.getAfterSalesStatus();
        Integer platformType = search.getPlatformType();
        String buyerName = search.getBuyerName();
        String buyerPhone = search.getBuyerPhone();
        Date createTimeBefore = search.getCreateTimeBefore();
        Date creteTimeAfter = search.getCreteTimeAfter();

        if(StringUtils.isNotBlank(oid)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getOid, oid);
        }
        if(StringUtils.isNotBlank(bid)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getBid, bid);
        }
        if(StringUtils.isNotBlank(channelId)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getChannelId, channelId);
        }
        if(StringUtils.isNotBlank(platformId)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getPlatformId, platformId);
        }
        if (StringUtils.isNotBlank(externalOrderId)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getExternalOrderId, externalOrderId);
        }
        if(Objects.nonNull(orderStatus)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getOrderStatus, orderStatus);
        }
        if(Objects.nonNull(afterSalesStatus)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getAfterSalesStatus, afterSalesStatus);
        }
        if(Objects.nonNull(platformType)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getPlatformType, platformType);
        }
        if(StringUtils.isNotBlank(buyerName)) {
            lambdaQueryWrapper.like(OrderMasterDO::getBuyerName, buyerName +"%");
        }
        if(StringUtils.isNotBlank(buyerPhone)) {
            lambdaQueryWrapper.eq(OrderMasterDO::getBuyerPhone, buyerPhone);
        }
        if(Objects.nonNull(createTimeBefore) && Objects.nonNull(creteTimeAfter)) {
            lambdaQueryWrapper.between(OrderMasterDO::getCreateTime, createTimeBefore, creteTimeAfter);
        }

        return lambdaQueryWrapper;
    }

    @Override
    protected List<String> doListFutureFlowPageNums(OrderSearchForm search, String lastFlowNum, Integer pageSize, Integer readNum) {
        return orderMasterMapper.findFutureFlowPageNums(search, lastFlowNum, pageSize, pageSize * readNum);
    }

    @Override
    protected List<OrderMasterDTO> doFlowSearch(OrderSearchForm search, String lastFlowNum, Integer pageSize) {
        List<OrderMasterDO> orderMasterDOList = orderMasterMapper.findForFlow(search, lastFlowNum, pageSize);
        return converter(orderMasterDOList);
    }
}
