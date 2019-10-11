package com.loafer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loafer.common.JSONResponse;
import com.loafer.dao.UserDao;
import com.loafer.entity.User;
import com.loafer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/10 14:18
 * @Version V1.0
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public boolean checkUser(User user) {
        User user1 = userDao.checkUser(user);
        if(user1 != null){
            return true;
        }
        return false;
    }

    @Override
    public User getUserByLoginName(String loginName) {
        return userDao.getUserByLoginName(loginName);
    }

    @Override
    public List<User> list(String loginName) {
        return userDao.list(loginName);
    }
}
