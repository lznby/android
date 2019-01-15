package com.lznbys.android;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.lznbys.android.dao")
public class AndroidApplication {
    public static void main(String[] args) {
        SpringApplication.run(AndroidApplication.class, args);
    }
}
