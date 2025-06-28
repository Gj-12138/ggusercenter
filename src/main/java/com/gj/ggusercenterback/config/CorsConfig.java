package com.gj.ggusercenterback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // 标记为Spring配置类，启动时自动加载
public class CorsConfig implements WebMvcConfigurer {
      /**
     * 全局CORS配置处理器
     * ▶︎ 核心作用：突破浏览器同源策略限制，允许指定域名的跨域请求
     * ▶︎ 典型场景：
     *   - 前端SPA（如vue.js）部署在独立域名
     *   - 微服务间跨域通信
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 对所有接口生效
                .allowCredentials(true)  // 允许携带Cookie（需配合严格域名限制）

                // 使用allowedOriginPatterns替代allowedOrigins，解决与allowCredentials的冲突问题
                // 警告：生产环境应指定具体域名（如"https://yourdomain.com"），避免使用"*"
                .allowedOriginPatterns("*")  // ③ 允许的源域名集合（关键安全配置）警告：生产环境应指定具体域名（如"https://yourdomain.com"），避免使用"*"
        
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 允许的HTTP方法
                .allowedHeaders("*")       // 允许所有请求头（如Authorization/Content-Type）（避免暴露业务敏感字段）
                .exposedHeaders("*");       // 暴露所有响应头给客户端（客户端可访问的自定义头）    
    }
}