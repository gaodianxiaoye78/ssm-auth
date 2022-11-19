package com.mrj.system.exception;

import com.mrj.common.result.Result;
import com.mrj.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @ExceptionHandler(AccessDeniedException.class)
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        e.printStackTrace();
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("您没有操作权限，请联系管理员mrj");
    }


}
