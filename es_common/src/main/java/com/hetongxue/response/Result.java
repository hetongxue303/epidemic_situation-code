package com.hetongxue.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 统一返回结果类
 * @Class: Result
 * @Author: hetongxue
 * @DateTime: 2022/9/9 4:32:41
 */
@Data
@Accessors(chain = true)
public class Result implements Serializable {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    private Result() {
    }

    /**
     * 成功返回
     **/
    public static Result Success() {
        return new Result().setCode(ResponseCode.OK.getCode()).setMessage(ResponseCode.OK.getMessage());
    }

    public static Result Success(Object data) {
        return new Result().setCode(ResponseCode.OK.getCode()).setMessage(ResponseCode.OK.getMessage()).setData(data);
    }

    public static Result Success(Object data, String message) {
        return new Result().setCode(ResponseCode.OK.getCode()).setMessage(message).setData(data);
    }

    /**
     * 失败返回
     **/
    public static Result Error() {
        return new Result().setCode(ResponseCode.BAD_REQUEST.getCode()).setMessage(ResponseCode.BAD_REQUEST.getMessage());
    }

    public static Result Error(Object data) {
        return new Result().setCode(ResponseCode.BAD_REQUEST.getCode()).setMessage(ResponseCode.BAD_REQUEST.getMessage()).setData(data);
    }

    public static Result Error(Object data, String message) {
        return new Result().setCode(ResponseCode.BAD_REQUEST.getCode()).setMessage(message).setData(data);
    }

}