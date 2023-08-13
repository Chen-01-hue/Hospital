package com.chen.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.chen")//扫描swagger
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.chen")
public class ServiceHospMain8201 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospMain8201.class,args);
    }
}
