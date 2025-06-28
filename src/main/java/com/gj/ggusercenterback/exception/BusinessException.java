package com.gj.ggusercenterback.exception;

import lombok.Getter;

@Getter  // 自动生成 getCode() 方法（Lombok 注解）
public class BusinessException extends RuntimeException {

    /**
     * 业务错误码（需全局唯一）
     * 作用：前端根据此码进行特定处理，如 40100 → 跳转登录页
     */
    private final int code;

    /**
     * 基础构造（自定义错误码+消息）
     * @param code    需遵循团队规范（如与 HTTP 状态码组合）
     * @param message 客户端展示信息（避免泄露敏感细节）
     */
    public BusinessException(int code, String message) {
        super(message);  // 异常消息存入父类
        this.code = code;
    }

    /**
     * 快捷构造（使用预定义错误枚举）
     * @param errorCode 统一错误码枚举（如 ErrorCode.PARAMS_ERROR）
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 扩展构造（覆盖枚举默认消息）
     * @param errorCode 错误类型枚举
     * @param message   补充上下文信息（如 "用户ID不能为空"）
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}