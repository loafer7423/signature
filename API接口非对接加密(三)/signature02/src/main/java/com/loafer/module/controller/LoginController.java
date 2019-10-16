package com.loafer.module.controller;

import com.loafer.common.JSONResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description [登录控制器]
 * @Author wangdong
 * @Date 2019/10/12 13:41
 * @Version V1.0
 **/
@RestController
public class LoginController {


    @RequestMapping("/login")
    public JSONResponse login(){
        return JSONResponse.ok();
    }

}
