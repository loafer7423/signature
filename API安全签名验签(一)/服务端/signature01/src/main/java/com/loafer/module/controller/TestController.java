package com.loafer.module.controller;

import com.loafer.common.JSONResponse;
import com.loafer.module.req.TestReq;
import com.loafer.module.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Description [一句话描述做什么]
 * @Author wangdong
 * @Date 2019/10/6 17:31
 * @Version V1.0
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("/list")
    public JSONResponse list(@RequestBody TestReq testReq){
        return testService.list(testReq);
    }


}
