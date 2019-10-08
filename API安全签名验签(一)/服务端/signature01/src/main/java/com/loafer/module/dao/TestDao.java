package com.loafer.module.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.loafer.common.JSONResponse;
import com.loafer.module.entity.TestEntity;
import com.loafer.module.req.TestReq;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName TestDao
 * @Description []
 * @Author wangdong
 * @Date 2019/10/6 18:01
 * @Version V1.0
 **/
@Repository
public interface TestDao extends BaseMapper<TestEntity> {
    @Select("select * from test")
    public List<TestEntity> list(TestReq testReq);
}
