package cloud.tianai.order.search.form;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/26 15:34
 * @Description: 订单搜索条件
 */
@Data
@Accessors(chain = true)
public class OrderSearchForm {

    private String oid;
    /** 用户ID. */
    private String uid;
    /** 商户ID. */
    private String bid;
    /** 渠道ID. */
    private String channelId;
    /** 平台ID. */
    private String platformId;
    /** 第三方订单ID. */
    private String externalOrderId;

    /** 订单状态. */
    private List<Integer> orderStatusList;
    /** 售后状态. */
    private Integer afterSalesStatus;
    /** 平台类型. */
    private Integer platformType;

    /** 买家名称. */
    private String buyerName;
    /** 买家手机号. */
    private String buyerPhone;

    /** 创建时间 开始. */
    private Date createTimeBefore;
    /** 创建时间结束. */
    private Date creteTimeAfter;
}
