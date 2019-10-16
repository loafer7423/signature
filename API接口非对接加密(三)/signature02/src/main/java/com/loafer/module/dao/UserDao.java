package com.loafer.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loafer.module.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserDao
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/12 11:48
 * @Version V1.0
 **/
@Repository
public interface UserDao  extends BaseMapper<User> {

    @Select("select * from user")
    public List<User> list(User user);
}
