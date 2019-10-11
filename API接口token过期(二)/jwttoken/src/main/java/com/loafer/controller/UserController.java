package com.loafer.controller;

import com.loafer.common.JSONResponse;
import com.loafer.entity.User;
import com.loafer.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName UserController
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/10 14:41
 * @Version V1.0
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public JSONResponse list(@RequestBody User user){
        System.out.println("进入了业务方法..."+user.getName());
        List<User> list = userService.list(user.getName());
        return JSONResponse.ok(list);
    }

}
