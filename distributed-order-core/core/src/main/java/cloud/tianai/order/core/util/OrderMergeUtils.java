package cloud.tianai.order.core.util;

import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderMasterDO;
import cloud.tianai.order.common.dto.OrderMasterDTO;
import org.springframework.beans.BeanUtils;

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

}
