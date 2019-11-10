package cloud.tianai.order.core.common.enums;

import cloud.tianai.order.common.util.enums.CodeEnum;
import lombok.Getter;

/**
 * @Author: 天爱有情
 * @Date: 2019/10/24 19:47
 * @Description: 售后状态
 */
@Getter
public enum AfterSalesStatusEnum implements CodeEnum {
    /**
     * 默认.
     */
    DEFAULT(0, "默认."),
    /**
     * 用户申请售后.
     */
    APPLY_FOR_AFTER_SALE(1, "用户申请售后."),
    /**
     * 已退款.
     */
    AGREE_REFUND(2, "同意退款."),
    /**
     * 已寄回.
     */
    RETURN_GOODS(3, "已寄回."),
    /**
     * 已退款.
     */
    REFUND(4, "已退款."),
    /**
     * 已解决.
     */
    SOLVE(5, "已解决."),
    /**
     * 已拒签.
     */
    REFUSE(6, "已拒签.");

    private Integer code;
    private String msg;

    AfterSalesStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
