package cloud.tianai.order.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/7 16:59
 * @Description: 分页返回数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageInfo<T> {

    /** 返回的集合对象. */
    private List<T> records;

    /** 当前满足条件总行数. */
    private Long total;

    /** 当前分页总页数. */
    private Long size;

    /** 当前页. */
    private Long current;

}
