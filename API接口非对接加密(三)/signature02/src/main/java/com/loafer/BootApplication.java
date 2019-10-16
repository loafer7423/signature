package com.loafer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.loafer.module.dao")
public class BootApplication {

    public static void main(String[] args){
        SpringApplication.run(BootApplication.class, args);
    }

}
