package com.loafer.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * @ClassName Request
 * @Description [请求参数]
 * @Author wangdong
 * @Date 2019/10/6 17:53
 * @Version V1.0
 **/
public class Request implements Serializable {

    @JSONField(serialize = false)
    @TableField(exist = false)
    private BaseParam base;

    public BaseParam getBase() {
        return base;
    }

    public void setBase(BaseParam base) {
        this.base = base;
    }
}