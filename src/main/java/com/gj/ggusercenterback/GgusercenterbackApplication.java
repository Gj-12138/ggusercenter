package com.gj.ggusercenterback;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.gj.ggusercenterback.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class GgusercenterbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgusercenterbackApplication.class, args);
    }

}
