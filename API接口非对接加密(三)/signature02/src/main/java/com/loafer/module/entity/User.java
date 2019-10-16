package com.loafer.module.entity;

import com.loafer.common.Request;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description [用户实体类]
 * @Author wangdong
 * @Date 2019/10/12 11:48
 * @Version V1.0
 **/
@Data
public class User extends Request implements Serializable {

    private Long userId;

    private String name;

    private int age;

    private String password;

    private String sex;

}
