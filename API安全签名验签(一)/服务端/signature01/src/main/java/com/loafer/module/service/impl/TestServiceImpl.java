package com.loafer.module.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.loafer.common.JSONResponse;
import com.loafer.module.dao.TestDao;
import com.loafer.module.entity.TestEntity;
import com.loafer.module.req.TestReq;
import com.loafer.module.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TestServiceImpl
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/6 18:00
 * @Version V1.0
 **/
@Service
public class TestServiceImpl extends ServiceImpl<TestDao, TestEntity> implements TestService {

    @Autowired
    private TestDao testDao;

    @Override
    public JSONResponse list(TestReq testReq) {
        try{
            List<TestEntity> list = testDao.list(testReq);
            return JSONResponse.ok(list);
        }catch (Exception e){
            e.printStackTrace();
            return JSONResponse.failure();
        }
    }

    @Override
    public JSONResponse delete(TestReq testReq) {
        return null;
    }

    @Override
    public JSONResponse detail(TestReq testReq) {
        return null;
    }

    @Override
    public JSONResponse update(TestReq testReq) {
        return null;
    }
}
