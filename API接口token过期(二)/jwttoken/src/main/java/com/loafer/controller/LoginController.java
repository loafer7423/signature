package com.loafer.controller;

import com.loafer.common.JSONResponse;
import com.loafer.entity.User;
import com.loafer.service.UserService;
import com.loafer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName LoginController
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/10 14:10
 * @Version V1.0
 **/
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    private JwtUtil jwtUtil = new JwtUtil();
    /**
     * 登陆接口
     *
     * @return token
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JSONResponse login(@RequestBody Map<String, String> map) {
        String loginName = map.get("loginName");
        String password = map.get("password");
        User user1 = new User();
        user1.setName(loginName);
        user1.setPassword(password);
        //身份验证是否成功
        boolean isSuccess = userService.checkUser(user1);
        if (isSuccess) {
            User user = userService.getUserByLoginName(loginName);
            if (user != null) {
                //生成token，返回给客户端
                String token = jwtUtil.generateToken(user.getId());
                if (token != null) {
                    return JSONResponse.ok(token);
                }
            }
        }
        //返回登陆失败消息
        return JSONResponse.info("登陆失败");
    }

}
