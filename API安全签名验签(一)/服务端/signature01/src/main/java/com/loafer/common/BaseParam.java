package com.loafer.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseParam
 * @Description [基础参数]
 * @Author wangdong
 * @Date 2019/10/6 17:34
 * @Version V1.0
 **/
@Data
public class BaseParam implements Serializable {

    /** 密钥Id*/
    private String secretId="AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA";
    /**当前时间戳*/
    private long timestamp;
    /**随机字符串*/
    private String nonce;
    /**签名*/
    private String signature;
    /**当前登录人用户ID*/
    private Long userId;
    /***用户来源**/
    private String from;
}
