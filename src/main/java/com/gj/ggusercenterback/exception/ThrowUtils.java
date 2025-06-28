package com.gj.ggusercenterback.exception;

/**
 * 异常断言工具类（统一异常抛出入口）
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常（通用方法）
     * @param condition        触发条件（true=抛异常）
     * @param runtimeException 要抛出的异常对象（支持任意RuntimeException子类）
     * 使用场景：需要灵活抛出非业务异常时（如直接抛IllegalArgumentException）
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛业务异常（推荐用法）
     * @param condition 触发条件（true=抛异常）
     * @param errorCode 预定义错误码枚举（含code/message）
     * 示例：
     * ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛业务异常（自定义消息）
     * @param condition 触发条件
     * @param errorCode 错误类型枚举（确定错误码）
     * @param message   补充错误详情（覆盖枚举默认消息）
     * 示例：
     * ThrowUtils.throwIf(id < 0, ErrorCode.PARAMS_ERROR, "ID不能为负数");
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}