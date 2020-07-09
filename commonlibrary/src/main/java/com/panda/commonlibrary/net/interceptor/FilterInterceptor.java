package com.panda.commonlibrary.net.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *  <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/2/25 16:23
 *     desc   : 参数拦截器
 *     version: 1.0     初始化
 *     params:  key:        value:
 *  <pre>
 */
public class FilterInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl.Builder httpBuilder = originalRequest.url().newBuilder();
//        httpBuilder.addQueryParameter("key", "afc28ae28c6f1b520dab5d1ed537f6c0");
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .url(httpBuilder.build());
        return chain.proceed(requestBuilder.build());
    }

}
