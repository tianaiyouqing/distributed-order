package cloud.tianai.order.business.core.util.converter;

import cloud.tianai.order.business.common.dataobject.BusinessOrderDetailDO;
import cloud.tianai.order.core.common.dataobject.OrderDetailDO;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/31 16:25
 * @Description:  OrderDetailDO 转 BusinessOrderDetailDO
 */
public class BusinessOrderDetailConverter implements Converter<OrderDetailDO, BusinessOrderDetailDO> {

    @Override
    public BusinessOrderDetailDO convert(OrderDetailDO source) {
        BusinessOrderDetailDO result = new BusinessOrderDetailDO();
        BeanUtils.copyProperties(source, result);
        return result;
    }

    public Collection<BusinessOrderDetailDO> convert(Collection<OrderDetailDO> source) {
        List<BusinessOrderDetailDO> result = new ArrayList<>(source.size());

        for (OrderDetailDO orderDetailDO : source) {
            BusinessOrderDetailDO bod = new BusinessOrderDetailDO();
            BeanUtils.copyProperties(orderDetailDO, bod);

            result.add(bod);
        }
        return result;
    }
}
