package com.loafer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loafer.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName UserDao
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/10 14:12
 * @Version V1.0
 **/
@Repository
public interface UserDao  extends BaseMapper<User> {
    @Select("select * from user where name=#{name} and password=#{password}")
    public User checkUser(User user);
    @Select("select * from user where name=#{name} ")
    User getUserByLoginName(String loginName);
    @Select("select * from user where name=#{loginName} ")
    List<User> list(String loginName);
}
