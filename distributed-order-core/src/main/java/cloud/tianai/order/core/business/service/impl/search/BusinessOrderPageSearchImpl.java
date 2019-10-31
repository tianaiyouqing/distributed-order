package cloud.tianai.order.core.business.service.impl.search;

import cloud.tianai.order.core.business.mapper.BusinessOrderDetailMapper;
import cloud.tianai.order.core.business.mapper.BusinessOrderMasterMapper;
import cloud.tianai.order.core.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.dto.OrderMasterDTO;
import cloud.tianai.order.core.exception.OrderSearchException;
import cloud.tianai.order.core.exception.UserOrderSearchException;
import cloud.tianai.order.core.search.AbstractOrderPageSearchService;
import cloud.tianai.order.core.search.form.OrderSearchForm;
import cloud.tianai.order.core.util.OrderMergeUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessOrderPageSearchImpl extends AbstractOrderPageSearchService {

    private final BusinessOrderMasterMapper businessOrderMasterMapper;

    @Override
    protected void checkParamOfThrow(OrderSearchForm search) throws OrderSearchException {
        if(Objects.isNull(search) || Objects.isNull(search.getBid())) {
            throw new UserOrderSearchException("商户库搜索数据失败， 商户ID为空");
        }
    }

    @Override
    protected IPage<OrderMasterDTO> doListOrderForPage(OrderSearchForm search, Integer pageNum, Integer pageSize) {
        Wrapper<BusinessOrderMasterDO> queryWrapper = warpSearchParam(search);
        Page<BusinessOrderMasterDO> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        IPage<BusinessOrderMasterDO> searchResult = businessOrderMasterMapper.selectPage(page, queryWrapper);

        return converter(searchResult);
    }

    private IPage<OrderMasterDTO> converter(IPage<BusinessOrderMasterDO> page) {
        Page<OrderMasterDTO> result = new Page<>();
        BeanUtils.copyProperties(page, result);

        List<BusinessOrderMasterDO> records = page.getRecords();

        List<OrderMasterDTO> collect = converter(records);
        result.setRecords(collect);
        return result;
    }
    private List<OrderMasterDTO> converter(List<BusinessOrderMasterDO> records) {
        List<OrderMasterDTO> collect = records.stream()
                .map(o -> OrderMergeUtils.merge(o, new OrderMasterDTO()))
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    protected List<String> doListFutureScrollPageNums(OrderSearchForm search, String lastFlowNum, Integer pageSize, Integer readNum) {
        List<String> oids = businessOrderMasterMapper.findFutureScrollPageNums(search, lastFlowNum, pageSize * readNum);
        List<String> result = new ArrayList<>(readNum);
        for (int i = 0; i < oids.size(); i++) {
            if((i + 1) % pageSize == 0) {
                result.add(oids.get(i));
            }
        }
        return result;
    }

    @Override
    protected List<OrderMasterDTO> doScrollSearch(OrderSearchForm search, String lastFlowNum, Integer pageSize) {
        List<BusinessOrderMasterDO> orderMasterDOList = businessOrderMasterMapper.findForScroll(search, lastFlowNum, pageSize);
        return converter(orderMasterDOList);
    }

    private Wrapper<BusinessOrderMasterDO> warpSearchParam(OrderSearchForm search) {
        LambdaQueryWrapper<BusinessOrderMasterDO> lambdaQueryWrapper = Wrappers.<BusinessOrderMasterDO>lambdaQuery();

        String bid = search.getBid();
        lambdaQueryWrapper.eq(BusinessOrderMasterDO::getBid, bid);

        String uid = search.getUid();
        String oid = search.getOid();
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
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getOid, oid);
        }
        if(StringUtils.isNotBlank(uid)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getUid, uid);
        }
        if(StringUtils.isNotBlank(channelId)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getChannelId, channelId);
        }
        if(StringUtils.isNotBlank(platformId)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getPlatformId, platformId);
        }
        if (StringUtils.isNotBlank(externalOrderId)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getExternalOrderId, externalOrderId);
        }
        if(Objects.nonNull(orderStatus)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getOrderStatus, orderStatus);
        }
        if(Objects.nonNull(afterSalesStatus)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getAfterSalesStatus, afterSalesStatus);
        }
        if(Objects.nonNull(platformType)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getPlatformType, platformType);
        }
        if(StringUtils.isNotBlank(buyerName)) {
            lambdaQueryWrapper.like(BusinessOrderMasterDO::getBuyerName, buyerName +"%");
        }
        if(StringUtils.isNotBlank(buyerPhone)) {
            lambdaQueryWrapper.eq(BusinessOrderMasterDO::getBuyerPhone, buyerPhone);
        }
        if(Objects.nonNull(createTimeBefore) && Objects.nonNull(creteTimeAfter)) {
            lambdaQueryWrapper.between(BusinessOrderMasterDO::getCreateTime, createTimeBefore, creteTimeAfter);
        }

        return lambdaQueryWrapper;
    }
}
