package com.hetongxue.handler;

import com.hetongxue.response.ResponseCode;
import com.hetongxue.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 全局异常处理程序
 * @Class: GlobalExceptionHandler
 * @Author: hetongxue
 * @DateTime: 2022/9/12 0:01:05
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @ExceptionHandler(RuntimeException.class)
    public Result runtimeException(RuntimeException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.Error().setMessage(ResponseCode.INTERNAL_SERVER_ERROR.getMessage()).setCode(ResponseCode.INTERNAL_SERVER_ERROR.getCode());
    }

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(NullPointerException e) {
        e.printStackTrace();
        log.error(ResponseCode.NULL_POINTER.getMessage());
        return Result.Error().setMessage(ResponseCode.NULL_POINTER.getMessage()).setCode(ResponseCode.NULL_POINTER.getCode());
    }

}