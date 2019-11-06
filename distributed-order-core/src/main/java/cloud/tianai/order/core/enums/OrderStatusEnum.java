package cloud.tianai.order.core.enums;

import cloud.tianai.order.core.util.enums.CodeEnum;
import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum {
    /**
     * 待发货.
     */
    OVERHANG(0, "待发货."),
    /**
     * 已签收.
     */
    FINISHED(1, "已签收"),
    /**
     * 关闭.
     */
    CANCEL(2, "关闭."),
    /**
     * 已发货.
     */
    ALREADY_SHIPPED(3, "已发货."),
    /**
     * 待确认异常.
     */
    ERROR(4, "待确认异常."),
    /**
     * 已确认异常.
     */
    ERROR_FINISHED(6, "已确认异常."),
    /**
     * 删除.
     */
    REMOVE(-1, "删除."),
    /**
     * 新订单.
     */
    NEW(7, "新订单.")

    ;
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer type, String msg) {
        this.code = type;
        this.msg = msg;
    }

}
