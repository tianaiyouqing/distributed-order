package cloud.tianai.order.business.core.mapper;

import cloud.tianai.order.business.common.dataobject.BusinessOrderMasterDO;
import cloud.tianai.order.search.form.OrderSearchForm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 15:05
 * @Description: 订单主信息 dao
 */
public interface BusinessOrderMasterMapper extends BaseMapper<BusinessOrderMasterDO> {

    List<String> findFutureScrollPageNums(@Param("search") OrderSearchForm search,
                                          @Param("lastFlowNum") String lastFlowNum,
                                          @Param("readNum") Integer readNum);

    List<BusinessOrderMasterDO> findForScroll(@Param("search") OrderSearchForm search,
                                              @Param("lastFlowNum") String lastFlowNum,
                                              @Param("pageSize") Integer pageSize);
}
