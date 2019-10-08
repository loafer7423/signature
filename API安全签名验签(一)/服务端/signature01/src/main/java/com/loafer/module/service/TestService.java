package com.loafer.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.loafer.common.JSONResponse;
import com.loafer.module.entity.TestEntity;
import com.loafer.module.req.TestReq;

/**
 * @ClassName TestService
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/6 17:45
 * @Version V1.0
 **/
public interface TestService extends IService<TestEntity> {

    JSONResponse list(TestReq testReq);

    JSONResponse delete(TestReq testReq);

    JSONResponse detail(TestReq testReq);

    JSONResponse update(TestReq testReq);
}
