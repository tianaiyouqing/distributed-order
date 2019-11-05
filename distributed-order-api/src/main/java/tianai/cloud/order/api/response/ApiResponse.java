package tianai.cloud.order.api.response;

import lombok.Data;

import java.io.Serializable;

/**
 * Title: ApiResponse
 * Description: API统一返回格式规定
 *
 * @author: 天爱有情
 * @date: 2018年10月29日14:01:06
 **/
@Data
public class ApiResponse<T> implements Serializable {
    /**
     * code码.
     */
    private Integer code;
    /**
     * 信息.
     */
    private String msg;
    /**
     * 成功时返回的数据.
     */
    private T data;

    public ApiResponse(Integer code, String errMsg, T data) {
        this.code = code;
        this.msg = errMsg;
        this.data = data;
    }

    public ApiResponse(ApiResponseStatusEnum statusEnum, T data) {
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getStandardMessage();
        this.data = data;
    }

    public ApiResponse() {
        this.code = ApiResponseStatusEnum.SUCCESS.getCode();
        this.msg = ApiResponseStatusEnum.SUCCESS.getStandardMessage();
    }

    public static <T> ApiResponse<T> ofMessage(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static <T> ApiResponse<T> ofError(String message) {
        return new ApiResponse(ApiResponseStatusEnum.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }

    public static <T> ApiResponse<T> ofCheckError(String message) {
        return new ApiResponse(ApiResponseStatusEnum.NOT_VALID_PARAM.getCode(), message, null);
    }

    public static <T> ApiResponse<T> ofSuccess(T data) {
        return new ApiResponse(ApiResponseStatusEnum.SUCCESS.getCode(), ApiResponseStatusEnum.SUCCESS.getStandardMessage(), data);
    }

    public static <T> ApiResponse<T> ofStatus(ApiResponseStatusEnum status) {
        return new ApiResponse(status.getCode(), status.getStandardMessage(), null);
    }

    public boolean isSuccess() {
        return ApiResponseStatusEnum.SUCCESS.getCode().equals(this.code);
    }
}