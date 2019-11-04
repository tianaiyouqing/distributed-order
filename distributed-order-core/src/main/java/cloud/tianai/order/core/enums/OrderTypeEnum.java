package cloud.tianai.order.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 天爱有情
 * @Date: 2019/11/4 11:15
 * @Description: 订单类型枚举
 */
@Getter
@AllArgsConstructor
public enum  OrderTypeEnum implements CodeEnum {

    /** 货到付款. */
    CASH_ON_DELIVERY(0, "货到付款"),

    /** 在线付款. */
    ONLINE_PAYMENT(1, "在线付款")
    ;
    private Integer code;
    private String msg;
}
