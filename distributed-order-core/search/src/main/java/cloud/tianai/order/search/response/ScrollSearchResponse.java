package cloud.tianai.order.search.response;

import cloud.tianai.order.search.form.OrderSearchForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/8 13:57
 * @Description: 滚动分页返回数据
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ScrollSearchResponse<T>  {

    /** 返回的data数据. */
    private List<T> data;

    /** 最后一个ID数. */
    private String lastFlowNum;

    /** 查询参数. */
    private OrderSearchForm searchForm;
}
