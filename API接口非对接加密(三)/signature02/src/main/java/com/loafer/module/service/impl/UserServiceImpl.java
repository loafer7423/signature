package com.loafer.module.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loafer.common.JSONResponse;
import com.loafer.module.dao.UserDao;
import com.loafer.module.entity.User;
import com.loafer.module.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/12 11:53
 * @Version V1.0
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> list(User user) {
        List<User> list = userDao.list(user);
        return list;
    }
}
