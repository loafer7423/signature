package com.loafer.module.controller;

import com.loafer.common.JSONResponse;
import com.loafer.module.entity.User;
import com.loafer.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName UserController
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/12 11:48
 * @Version V1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/list")
    public JSONResponse list(@RequestBody User user, HttpServletRequest request){
        System.out.println("===========================我是业务方法=========================");
        System.out.println("uid="+user.getBase().getUid());
        System.out.println("name="+user.getBase().getName());
        System.out.println("性别是："+user.getSex());
        List<User> list = userService.list(user);
        return JSONResponse.ok(list);
    }


}
