package com.panda.commonlibrary.net.exception;


import com.panda.commonlibrary.net.HttpConfig;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/02/22
 *     desc   : token错误异常
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public class TokenInvalidException extends BaseException {
    public TokenInvalidException () {
        super("", HttpConfig.CODE_TOKEN_INVALID);
    }
}
