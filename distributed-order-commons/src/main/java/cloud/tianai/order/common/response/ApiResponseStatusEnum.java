package cloud.tianai.order.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 统一返回code枚举
 *
 * @author 天爱有情
 * @create 2018-07-30 17:01
 **/
@Getter
@AllArgsConstructor
public enum ApiResponseStatusEnum {

    /**
     * 服务器异常.
     */
    SERVER_ERROR(-1, "服务器异常. "),
    //============================
    //统一常用code码
    /**
     * 成功.
     */
    SUCCESS(200, "OK"),
    /**
     * 错误的请求.
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * 未找到.
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * 未知的内部错误.
     */
    INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),


    //============================
    // 系统相关通用异常， 4开头
    /**
     * 没有效的参数.
     */
    NOT_VALID_PARAM(40005, "Not valid Params"),
    /**
     * 操作不支持.
     */
    NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),;
    private Integer code;
    private String standardMessage;
}