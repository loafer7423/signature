package com.loafer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @ClassName SignatureApplication
 * @Description [数据签名启动类]
 * @Author wangdong
 * @Date 2019/10/6 16:41
 * @Version V1.0
 **/
@SpringBootApplication
@ServletComponentScan
@MapperScan("com.loafer.module.dao")
public class SignatureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignatureApplication.class, args);
    }

}
