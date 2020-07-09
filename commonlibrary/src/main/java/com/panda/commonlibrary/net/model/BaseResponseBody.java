package com.panda.commonlibrary.net.model;

import com.google.gson.annotations.SerializedName;

/**
 *  <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/3/1 14:35
 *     desc   : 返回数据的基类
 *     version: 1.0     初始化
 *     params:  key:        value:
 *  <pre>
 */
public class BaseResponseBody<T> {

    @SerializedName("errorCode")
    private String code;

    @SerializedName("errorMsg")
    private String msg;

    @SerializedName("data")
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
