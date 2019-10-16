package com.loafer.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseParam
 * @Description [请求接口通用参数]
 * @Author wangdong
 * @Date 2019/10/12 13:45
 * @Version V1.0
 **/
@Data
public class BaseParam implements Serializable {

    private String uid;

    private String name;

}
