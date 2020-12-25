package com.panda.commonlibrary.net;

import com.panda.commonlibrary.BuildConfig;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/02/22
 *     desc   : http 配置类
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public class HttpConfig {
    public static final long READ_TIMEOUT = 6000;
    public static final long WRITE_TIMEOUT = 6000;
    public static final long CONNECT_TIMEOUT = 6000;

    static {
        if (BuildConfig.IS_DEBUG) {
            //测试地址
            BASE_URL_RELEASE = "https://www.wanandroid.com";
        } else {
            //正式地址
            BASE_URL_RELEASE = "https://www.wanandroid.com";
        }
    }

    public static String BASE_URL_RELEASE = "https://www.wanandroid.com";
    public static final boolean DEBUG = true;
    public static final String CODE_SUCCESS = "0";//成功
    public static final String CODE_TOKEN_INVALID = "10001";//token错误
    public static final String CODE_UNKNOWN = "1";//未知错误
}
