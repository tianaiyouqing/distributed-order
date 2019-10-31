package cloud.tianai.order.core.util.converter;

import cloud.tianai.order.core.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 16:25
 * @Description:  OrderMasterDO 转 BusinessOrderMasterDO
 */
public class BusinessOrderMasterConverter implements Converter<OrderMasterDO, BusinessOrderMasterDO> {
    @Override
    public BusinessOrderMasterDO convert(OrderMasterDO source) {
        BusinessOrderMasterDO result = new BusinessOrderMasterDO();
        BeanUtils.copyProperties(source, result);
        return result;
    }
}
