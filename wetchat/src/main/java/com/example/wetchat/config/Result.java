package com.example.wetchat.config;

import lombok.Data;
import sun.plugin2.message.Message;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result<T> {
    /**
     * 请求是否成功
     * true:成功
     * false：失败
     */
    private boolean success;

    /**
     * 状态码
     * 成功：200
     * 失败：其他
     */
    private int code;

    /**
     * 失败状态码描述
     * 如果成功不返回
     * 失败返回状态码对应的msg消息
     */
    private String message;

    /**
     * 请求数据的结果
     */
    private T data;

    public Result(boolean success, int code) {
        this.setSuccess(success);
        this.setCode(code);
    }

    public Result(boolean success, int code, T data) {
        this.setSuccess(success);
        this.setCode(code);
        this.setData(data);
    }

    public Result(boolean success, int code, String message, T data) {
        this.setSuccess(success);
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }


    public Result(boolean success, int code, String message) {
        this.setSuccess(success);
        this.setCode(code);
        this.setMessage(message);
    }

    /**
     * 构造器私有
     */
    private Result() {

    }

    //通用返回失败，未知错误
    public static <T> Result<T> error(String message) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(HttpStatusEnum.UNKNOWN_ERROR.code());
        if (message == null) {
            result.setMessage(HttpStatusEnum.UNKNOWN_ERROR.reasonPhraseCN());
        } else {
            result.setMessage(message);
        }

        return result;
    }

    public static <T> Result<T> success() {
        return new Result<T>(true, 200);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(true, 200, data);
    }

    public static <T> Result<T> success(HttpStatusEnum httpStatusEnum, T data) {
        return new Result<T>(true, httpStatusEnum.code(), httpStatusEnum.reasonPhraseCN(), data);
    }

    public static <T> Result<T> success(HttpStatusEnum httpStatusEnum) {
        return new Result<T>(true, httpStatusEnum.code(), httpStatusEnum.reasonPhraseCN());
    }

    public static <T> Result<T> success(HttpStatusEnum httpStatusEnum, String message) {
        return new Result<T>(true, httpStatusEnum.code(), message);
    }

    public static <T> Result<T> fail(HttpStatusEnum httpStatusEnum) {
        return new Result<T>(false, httpStatusEnum.code(), httpStatusEnum.reasonPhraseCN());
    }

    public static <T> Result<T> fail(HttpStatusEnum httpStatusEnum, String message) {
        return new Result<T>(false, httpStatusEnum.code(), message);
    }

}
