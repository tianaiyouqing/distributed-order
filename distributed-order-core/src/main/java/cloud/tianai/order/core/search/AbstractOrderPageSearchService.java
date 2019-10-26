package cloud.tianai.order.core.search;

import cloud.tianai.order.core.dto.OrderMasterDTO;
import cloud.tianai.order.core.exception.OrderSearchException;
import cloud.tianai.order.core.search.form.OrderSearchForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public abstract class AbstractOrderPageSearchService implements OrderPageSearchService {

    private Integer minPageNum = 1;
    private Integer minPageSize = 1;
    private Integer minFlowRedNum = 1;
    private Integer defaultPageSize = 20;
    private Integer defaultFlowPageNum = 5;

    public AbstractOrderPageSearchService(Integer minPageNum,
                                          Integer minPageSize,
                                          Integer minFlowRedNum,
                                          Integer defaultPageSize,
                                          Integer defaultFlowPageNum) {
        this.minPageNum = minPageNum;
        this.minPageSize = minPageSize;
        this.minFlowRedNum = minFlowRedNum;
        this.defaultPageSize = defaultPageSize;
        this.defaultFlowPageNum = defaultFlowPageNum;
    }

    public AbstractOrderPageSearchService() {
    }

    @Override
    public IPage<OrderMasterDTO> listOrderForPage(OrderSearchForm search, Integer pageNum, Integer pageSize) {
        checkParamOfThrow(search);
        pageNum = useDefaultPageNumIfNecessary(pageNum);
        pageSize = useDefaultPageSizeIfNecessary(pageSize);


        return doListOrderForPage(search, pageNum, pageSize);
    }


    @Override
    public List<String> listFutureScrollPageNums(OrderSearchForm search, String lastFlowNum, Integer pageSize, Integer readNum) {
        checkParamOfThrow(search);
        pageSize = useDefaultPageSizeIfNecessary(pageSize);
        readNum = useDefaultFlowReadNumIfNecessary(readNum);
        return doListFutureScrollPageNums(search, lastFlowNum, pageSize, readNum);
    }

    @Override
    public List<OrderMasterDTO> scrollSearch(OrderSearchForm search, String lastFlowNum, Integer pageSize) {
        checkParamOfThrow(search);
        pageSize = useDefaultPageSizeIfNecessary(pageSize);
        return doScrollSearch(search, lastFlowNum, pageSize);
    }

    protected Integer useDefaultFlowReadNumIfNecessary(Integer readNum) {
        if(Objects.isNull(readNum) || readNum < minFlowRedNum) {
            readNum = defaultFlowPageNum;
        }
        return readNum;
    }



    protected Integer useDefaultPageSizeIfNecessary(Integer pageSize) {
        if(Objects.isNull(pageSize) || pageSize < minPageSize) {
            pageSize = defaultPageSize;
        }
        return pageSize;
    }

    protected Integer useDefaultPageNumIfNecessary(Integer pageNum) {
        if(Objects.isNull(pageNum) || pageNum < minPageNum) {
            pageNum = minPageNum;
        }
        return pageNum;
    }
    // ======================================
    // 抽象方法
    // ======================================

    /**
     * 校验参数， 如果校验失败抛出异常
     * @param search 搜索条件
     */
    protected abstract void checkParamOfThrow(OrderSearchForm search) throws OrderSearchException;
    
    /**
     * 普通的分页查询
     *
     * @param search   搜索条件
     * @param pageNum  当前页量
     * @param pageSize 每页数量
     * @return Page<OrderMasterDTO>
     */
    protected abstract IPage<OrderMasterDTO> doListOrderForPage(OrderSearchForm search, Integer pageNum, Integer pageSize);

    /**
     * 读取流式分页的页码
     *
     * @param search      搜索条件
     * @param lastFlowNum 上一个流式的id标识， 可以为空
     * @param pageSize    分页的数量
     * @param readNum     读取的数量
     * @return List<String>
     */
    protected abstract List<String> doListFutureScrollPageNums(OrderSearchForm search, String lastFlowNum, Integer pageSize, Integer readNum);

    /**
     * 流式分页查询
     *
     * @param search      搜索条件
     * @param lastFlowNum 上一个流式分页的标识
     * @param pageSize    显示多少条
     * @return List<OrderMasterDTO>
     */
    protected abstract List<OrderMasterDTO> doScrollSearch(OrderSearchForm search, String lastFlowNum, Integer pageSize);
}
