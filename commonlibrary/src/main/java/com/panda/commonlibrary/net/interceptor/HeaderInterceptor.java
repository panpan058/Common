package com.panda.commonlibrary.net.interceptor;


import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/2/25 16:24
 *     desc   : 头部拦截器
 *     version: 1.0     初始化
 *     params:  key:        value:
 *  <pre>
 */
public class HeaderInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder requestBuilder = originalRequest.newBuilder();
//                .addHeader("Accept-Encoding", "gzip")
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .method(originalRequest.method(), originalRequest.body());
        return chain.proceed(requestBuilder.build());
    }

}
