package com.loafer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.loafer.dao")
public class SpringbootApplication{

    public static void main(String[] args){
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
