package com.loafer.module.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @ClassName TestEntity
 * @Description [测试实体类]
 * @Author wangdong
 * @Date 2019/10/6 17:33
 * @Version V1.0
 **/
@Data
public class TestEntity implements Serializable {

    private Long tid;

    private String name;

    private LocalDate createTime;
}
