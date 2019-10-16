package com.loafer.common;

import lombok.Data;

/**
 * @ClassName JSONResponse
 * @Description [表示服务端基于JSON的响应]
 * @Author wangdong
 * @Date 2019/10/6 17:39
 * @Version V1.0
 **/
@Data
public final class JSONResponse<T> {

    /**服务执行失败时的响应码*/
    private static final int FAILURE_CODE =5001 ;

    /**服务执行成功时的响应码*/
    private static final int SUCCESS_CODE =1100;

    /**服务执行成功时默认响应的消息*/
    private static final String SUCCESS_MESSAGE = "SUCCESS";

    /**服务执行成功时默认响应的消息*/
    private static final String FAILURE_MESSAGE = "未知异常，请联系管理员...";

    /**状态码*/
    private int code;
    /**提示信息*/
    private String message;
    /**资源数据*/
    private T data;

    private JSONResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static JSONResponse ok() {
        return new JSONResponse(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static JSONResponse ok(Object data) {
        return new JSONResponse(SUCCESS_CODE, SUCCESS_MESSAGE).data(data);
    }

    public static JSONResponse ok(int code,Object data) {
        return new JSONResponse(code, SUCCESS_MESSAGE).data(data);
    }

    public static JSONResponse failure(int code,String message) {
        return new JSONResponse(code, message);
    }

    public static JSONResponse failure(String message) {
        return new JSONResponse(FAILURE_CODE, message);
    }

    public static JSONResponse failure() {
        return new JSONResponse(FAILURE_CODE, FAILURE_MESSAGE);
    }

    public JSONResponse<T> data(T data) {
        this.data = data;
        return this;
    }
}