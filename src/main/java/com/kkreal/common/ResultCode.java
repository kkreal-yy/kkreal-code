package com.kkreal.common;

/**
 * 状态码枚举
 */
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),
    
    // 客户端错误 4xx
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),
    
    // 服务端错误 5xx
    ERROR(500, "系统内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),
    
    // 业务错误 1xxx
    BUSINESS_ERROR(1000, "业务处理失败"),
    VALIDATION_ERROR(1001, "数据验证失败"),
    DUPLICATE_KEY_ERROR(1002, "数据已存在"),
    DATA_NOT_FOUND(1003, "数据不存在"),
    
    // 数据库错误 2xxx
    DATABASE_ERROR(2000, "数据库操作失败"),
    SQL_ERROR(2001, "SQL执行错误");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
