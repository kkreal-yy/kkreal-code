package com.kkreal.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 日志管理工具类
 */
public class LogUtil {
    
    private static final String TRACE_ID = "traceId";
    
    /**
     * 获取Logger实例
     * @param clazz 类
     * @return Logger
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
    
    /**
     * 设置追踪ID
     * @param traceId 追踪ID
     */
    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }
    
    /**
     * 获取追踪ID
     * @return 追踪ID
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }
    
    /**
     * 清除追踪ID
     */
    public static void clearTraceId() {
        MDC.remove(TRACE_ID);
    }
    
    /**
     * 生成追踪ID
     * @return 追踪ID
     */
    public static String generateTraceId() {
        return String.valueOf(System.currentTimeMillis()) + "-" + 
               Thread.currentThread().getId() + "-" + 
               Math.abs((int) (Math.random() * 100000));
    }
    
    /**
     * 记录业务日志
     * @param logger 日志记录器
     * @param message 日志消息
     * @param params 参数
     */
    public static void logBusinessInfo(Logger logger, String message, Object... params) {
        if (logger.isInfoEnabled()) {
            logger.info("[BUSINESS] " + message, params);
        }
    }
    
    /**
     * 记录业务错误日志
     * @param logger 日志记录器
     * @param message 日志消息
     * @param params 参数
     */
    public static void logBusinessError(Logger logger, String message, Object... params) {
        logger.error("[BUSINESS] " + message, params);
    }
    
    /**
     * 记录操作日志
     * @param logger 日志记录器
     * @param operation 操作类型
     * @param userId 用户ID
     * @param resource 资源
     * @param result 操作结果
     */
    public static void logOperation(Logger logger, String operation, String userId, String resource, String result) {
        String message = String.format("OPERATION - User: %s, Operation: %s, Resource: %s, Result: %s", 
                userId, operation, resource, result);
        logger.info("[OPERATION] " + message);
    }
    
    /**
     * 记录性能日志
     * @param logger 日志记录器
     * @param operation 操作名称
     * @param duration 耗时(毫秒)
     * @param params 附加参数
     */
    public static void logPerformance(Logger logger, String operation, long duration, Object... params) {
        String message = String.format("PERFORMANCE - Operation: %s, Duration: %d ms", operation, duration);
        if (params.length > 0) {
            message += ", Params: " + java.util.Arrays.toString(params);
        }
        logger.info("[PERFORMANCE] " + message);
    }
}