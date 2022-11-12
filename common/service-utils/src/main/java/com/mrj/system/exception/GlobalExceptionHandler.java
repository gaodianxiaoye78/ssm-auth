package com.mrj.system.exception;

import com.mrj.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public Result globalError(Exception e) {
        e.printStackTrace();
        return Result.fail().message("出现未知异常，请联系管理员mrj");
    }

    @ExceptionHandler(OperationException.class)
    public Result operationError(OperationException e) {
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }



}
