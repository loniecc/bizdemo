package com.lonie.biz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.lonie.biz"})
//@MapperScan("com.lonie.biz")
public class BizdemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BizdemoApplication.class, args);
    }
}
