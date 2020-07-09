package com.panda.commonlibrary.net.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 *  <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/2/25 16:24
 *     desc   : 请求拦截器
 *     version: 1.0     初始化
 *     params:  key:        value:
 *  <pre>
 */
public class HttpInterceptor implements Interceptor {

    private static final String TAG = "HttpInterceptor";

    public HttpInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = null;
        try {
            originalResponse = chain.proceed(request);
        } catch (Exception e) {
            Log.e(TAG, "intercept: " + e);
            throw e;
        }
        if (originalResponse.code() != 200) {
            Log.e(TAG, "intercept: " + originalResponse.code());
        }
        BufferedSource source = originalResponse.body().source();
        source.request(Integer.MAX_VALUE);
        String byteString = source.buffer().snapshot().utf8();
        ResponseBody responseBody = ResponseBody.create(null, byteString);
        return originalResponse.newBuilder().body(responseBody).build();
    }

}