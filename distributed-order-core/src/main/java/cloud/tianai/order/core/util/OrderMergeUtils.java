package cloud.tianai.order.core.util;

import cloud.tianai.order.core.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.dto.OrderMasterDTO;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 11:49
 * @Description: 数据合并
 */
public class OrderMergeUtils {

    public static OrderMasterDO merge(OrderMasterDO oldData, OrderMasterDO mergeData) {
        OrderMasterDO newData = oldData;
        BeanUtils.copyProperties(mergeData, newData);
        return newData;
    }

    public static OrderDetailDO merge(OrderDetailDO oldOrderDetail, OrderDetailDO update) {
        OrderDetailDO newData = oldOrderDetail;
        BeanUtils.copyProperties(update, newData);
        return newData;
    }

    public static OrderMasterDTO merge(OrderMasterDO orderMasterDO, OrderMasterDTO orderMasterDTO) {
        BeanUtils.copyProperties(orderMasterDO, orderMasterDTO);
        return orderMasterDTO;
    }

    public static OrderMasterDTO merge(BusinessOrderMasterDO orderMasterDO, OrderMasterDTO orderMasterDTO) {
        BeanUtils.copyProperties(orderMasterDO, orderMasterDTO);
        return orderMasterDTO;
    }
}
