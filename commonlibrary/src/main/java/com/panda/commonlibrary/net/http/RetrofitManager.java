package com.panda.commonlibrary.net.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.panda.commonlibrary.net.api.ApiInterface;
import com.panda.commonlibrary.net.HttpConfig;
import com.panda.commonlibrary.net.interceptor.FilterInterceptor;
import com.panda.commonlibrary.net.interceptor.HeaderInterceptor;
import com.panda.commonlibrary.net.interceptor.HttpInterceptor;
import com.panda.commonlibrary.net.transform.NullTypeAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/02/22
 *     desc   : retrofit创建 单例模式
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public class RetrofitManager {
    private Retrofit.Builder mBuilder;

    private RetrofitManager () {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(HttpConfig.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(HttpConfig.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new FilterInterceptor())
//                .addInterceptor(Pandora.get().getInterceptor())
                .retryOnConnectionFailure(true);

        if (HttpConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .registerTypeAdapterFactory(new NullTypeAdapterFactory())
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        OkHttpClient client = builder.build();
        mBuilder = new Retrofit.Builder()
                .client(client)
                .baseUrl(HttpConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    private static class RetrofitManagerHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static final RetrofitManager getInstance () {
        return RetrofitManagerHolder.INSTANCE;
    }

    public ApiInterface create() {
        return create(ApiInterface.class);
    }
    public <T> T create (Class<T> clazz) {
        return create(HttpConfig.BASE_URL, clazz);
    }

    public <T> T create (String basUrl, Class<T> clazz) {
        return mBuilder.baseUrl(basUrl).build().create(clazz);
    }

}
