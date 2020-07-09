package com.panda.commonlibrary.net.transform;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 *  <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/3/1 14:35
 *     desc   : 对返回null的处理
 *     version: 1.0     初始化
 *     params:  key:        value:
 *  <pre>
 */
public class NullTypeAdapterFactory<T> implements TypeAdapterFactory {

    @Override
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType= (Class<T>) type.getRawType();
        if (rawType!=String.class) {
            return null;
        }
        return (TypeAdapter<T>) new NullAdapter();
    }
}
