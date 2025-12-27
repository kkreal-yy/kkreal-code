package com.kkreal.exception;

import com.kkreal.common.Result;
import com.kkreal.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleBusinessException(BusinessException e) {
        logger.error("业务异常：{}", e.getMessage(), e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常（@Valid）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数校验异常：{}", e.getMessage());
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "参数校验失败";
        return Result.error(ResultCode.VALIDATION_ERROR.getCode(), errorMessage);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleBindException(BindException e) {
        logger.error("参数绑定异常：{}", e.getMessage());
        FieldError fieldError = e.getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "参数绑定失败";
        return Result.error(ResultCode.BAD_REQUEST.getCode(), errorMessage);
    }

    /**
     * 处理数据库唯一键冲突异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error("数据库唯一键冲突：{}", e.getMessage(), e);
        return Result.error(ResultCode.DUPLICATE_KEY_ERROR);
    }

    /**
     * 处理SQL异常
     */
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleSQLException(SQLException e) {
        logger.error("数据库异常：{}", e.getMessage(), e);
        return Result.error(ResultCode.DATABASE_ERROR);
    }

    /**
     * 处理404异常
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        logger.error("请求的资源不存在：{}", e.getRequestURL());
        return Result.error(ResultCode.NOT_FOUND);
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("请求方法不支持：{}", e.getMessage());
        return Result.error(ResultCode.METHOD_NOT_ALLOWED.getCode(), 
                "不支持" + e.getMethod() + "请求方法");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleNullPointerException(NullPointerException e) {
        logger.error("空指针异常：{}", e.getMessage(), e);
        return Result.error(ResultCode.ERROR.getCode(), "系统内部错误：空指针异常");
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error("非法参数异常：{}", e.getMessage(), e);
        return Result.error(ResultCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleRuntimeException(RuntimeException e) {
        logger.error("运行时异常：{}", e.getMessage(), e);
        return Result.error(ResultCode.ERROR.getCode(), "系统运行异常：" + e.getMessage());
    }

    /**
     * 处理未知异常（兜底）
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e) {
        logger.error("系统异常：{}", e.getMessage(), e);
        return Result.error(ResultCode.ERROR);
    }
}
