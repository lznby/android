package com.lznbys.android.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 1.使用@ControllerAdvice定义统一的异常处理类
 * 2.@ExceptionHandler 用来定义函数针对的异常类型
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public BaseResponseEntity<String> jsonErrorHandler(HttpServletRequest request , BaseException e) throws Exception{
        BaseResponseEntity<String> responseEntity = new BaseResponseEntity<>();
        responseEntity.setMessage(e.getMessage());
        responseEntity.setCode(ResponseCode.ERROR);
        responseEntity.setData(null);
        return responseEntity;
    }
}
