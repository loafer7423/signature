package com.loafer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loafer.entity.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/10 14:16
 * @Version V1.0
 **/
public interface UserService extends IService<User> {

    boolean checkUser(User user);

    User getUserByLoginName(String loginName);

    List<User> list(String loginName);
}
