package com.panda.commonlibrary.net.transform;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

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
public class NullAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (null == value) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        if (JsonToken.NULL == in.peek()) {
            in.nextNull();
            return "";
        }
        return in.nextString();
    }
}
