package com.example.wetchat.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {
    /*
    通常异常处理方法
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error("");
    }

    /**
     * 指定异常处理方法
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Result error(NullPointerException e){
        e.printStackTrace();
        return Result.fail(HttpStatusEnum.NULL);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public Result error(IndexOutOfBoundsException e){
        e.printStackTrace();
        return Result.fail(HttpStatusEnum.HTTP_STATUS_ENUM);
    }

    /**
     * 自定义异常处理方法
     */
    @ExceptionHandler(CMSException.class)
    @ResponseBody
    public Result error(CMSException e){
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
