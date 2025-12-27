package com.kkreal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kkreal.mapper")
public class KkrealCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(KkrealCodeApplication.class, args);
    }
}
