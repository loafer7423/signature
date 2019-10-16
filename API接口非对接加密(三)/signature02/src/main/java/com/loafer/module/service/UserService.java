package com.loafer.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loafer.common.JSONResponse;
import com.loafer.module.entity.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/12 11:51
 * @Version V1.0
 **/
public interface UserService extends IService<User> {

    List<User> list(User user);
}
