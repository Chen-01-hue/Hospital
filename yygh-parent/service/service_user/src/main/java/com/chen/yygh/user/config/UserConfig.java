package com.chen.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.chen.yygh.user.mapper")
public class UserConfig {
}
