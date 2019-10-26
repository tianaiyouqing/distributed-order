package cloud.tianai.order.core.basic.impl;

import cloud.tianai.order.core.basic.BasicOrderService;
import cloud.tianai.order.core.dataobject.OrderDetailDO;
import cloud.tianai.order.core.dataobject.OrderMasterDO;
import cloud.tianai.order.core.exception.OrderSaveException;
import cloud.tianai.order.core.warpper.OrderWrapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/25 13:45
 * @Description: 抽象的技术业务层
 */
public abstract class AbstractBasicOrderService extends AbstractOrderStatusService implements BasicOrderService {
}
