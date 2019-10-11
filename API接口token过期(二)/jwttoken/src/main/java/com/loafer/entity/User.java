package com.loafer.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName User
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/10 14:11
 * @Version V1.0
 **/
@Data
public class User implements Serializable {

    private String id;

    private String name;

    private String password;

    private Integer age;
}
