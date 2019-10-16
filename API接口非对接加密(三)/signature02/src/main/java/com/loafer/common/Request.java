package com.loafer.common;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

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
