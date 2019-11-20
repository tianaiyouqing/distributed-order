package cloud.tianai.order.core.pay.test.controller;

import cloud.tianai.order.common.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse ex(RuntimeException ex) {
        System.out.println("进入异常捕捉");
        return ApiResponse.ofError(ex.getMessage());
    }
}
