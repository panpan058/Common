package com.panda.commonlibrary.net.exception;


import com.panda.commonlibrary.net.HttpConfig;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/02/22
 *     desc   : 异常基类
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public class BaseException extends RuntimeException {
    private String errCode = HttpConfig.CODE_UNKNOWN;

    public BaseException () {
    }

    public BaseException (String message, String errcode) {
        super(message);
        this.errCode = errcode;
    }

    public String getErrCode () {
        return errCode == null ? "" : errCode;
    }

    public void setErrCode (String errCode) {
        this.errCode = errCode;
    }
}
