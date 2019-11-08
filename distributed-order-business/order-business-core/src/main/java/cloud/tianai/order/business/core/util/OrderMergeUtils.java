package cloud.tianai.order.business.core.util;

import cloud.tianai.order.business.common.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.common.dto.OrderMasterDTO;
import org.springframework.beans.BeanUtils;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 11:49
 * @Description: 数据合并
 */
public class OrderMergeUtils {

    public static OrderMasterDTO merge(BusinessOrderMasterDO orderMasterDO, OrderMasterDTO orderMasterDTO) {
        BeanUtils.copyProperties(orderMasterDO, orderMasterDTO);
        return orderMasterDTO;
    }
}
