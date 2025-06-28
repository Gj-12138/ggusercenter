package com.gj.ggusercenterback.exception;

import lombok.Getter;

@Getter  // Lombok 自动生成 getter 方法（无需手动写 getCode()/getMessage()）
public enum ErrorCode {

    // ================== 成功状态码 ==================
    SUCCESS(0, "ok"),          // 正常响应（非 HTTP 200，需与前端约定业务成功标识）

    // ================== 客户端错误 ==================
    PARAMS_ERROR(40000, "请求参数错误"),       // 对应 HTTP 400（前端传参格式/内容错误）
    NOT_LOGIN_ERROR(40100, "未登录"),        // 对应 HTTP 401（Token 缺失或失效）
    NO_AUTH_ERROR(40101, "无权限"),          // 对应 HTTP 403（权限不足，细粒度控制）
    NOT_FOUND_ERROR(40400, "请求数据不存在"),  // 对应 HTTP 404（资源未找到）
    FORBIDDEN_ERROR(40300, "禁止访问"),       // 对应 HTTP 403（IP限制/黑名单场景）

    // ================== 服务端错误 ==================
    SYSTEM_ERROR(50000, "系统内部异常"),      // 兜底错误（数据库崩溃等未捕获异常）
    OPERATION_ERROR(50001, "操作失败");      // 明确业务失败（如扣减库存不足）

    /**
     * 业务状态码（需全局唯一）
     * 编码规则：HTTP状态码（前3位） + 自定义细分码（后2位）
     * 示例：40100 → 401（未授权） + 00（细分类型）
     */
    private final int code;

    /**
     * 客户端展示信息（需简洁明确）
     * 生产环境建议避免暴露敏感细节（如数据库错误信息）
     */
    private final String message;

    /**
     * 枚举构造函数（自动私有化）
     * @param code 必须遵循编码规范
     * @param message 不超过20字
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}