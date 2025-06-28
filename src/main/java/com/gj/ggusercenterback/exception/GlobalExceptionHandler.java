package com.gj.ggusercenterback.exception;

import com.gj.ggusercenterback.common.BaseResponse;
import com.gj.ggusercenterback.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器（AOP 切面实现）
 * 作用：拦截所有Controller层异常，统一响应格式
 */
@RestControllerAdvice  // = @ControllerAdvice + @ResponseBody
@Slf4j  // 自动注入日志对象
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常（自定义异常类）
     * @param e 业务异常实例（包含错误码和消息）
     * @return 前端需要的标准错误响应体
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("业务异常[code={}]：{}", e.getCode(), e.getMessage(), e);  // 打印堆栈详情
        return ResultUtils.error(e.getCode(), e.getMessage());  // 透传业务错误码
    }

    /**
     * 兜底异常处理（所有未显式捕获的RuntimeException）
     * @param e 运行时异常（如NPE、IllegalArgumentException）
     * @return 屏蔽细节，返回通用系统错误
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("系统运行时异常：", e);  // 生产环境需监控报警
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误");  // 避免暴露敏感信息
    }
}