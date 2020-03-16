package com.lonie.biz.bizdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {"com.lonie.biz"})
@MapperScan("com.lonie.biz")
public class BizdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BizdemoApplication.class, args);
    }
}
