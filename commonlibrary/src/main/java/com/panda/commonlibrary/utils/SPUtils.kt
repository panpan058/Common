package com.panda.commonlibrary.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV

object SPUtils {
    fun put(key: String, value: Any) {
        try {
            when (value) {
                is String -> MMKV.defaultMMKV().encode(key, value)
                is Boolean -> MMKV.defaultMMKV().encode(key, value)
                is Int -> MMKV.defaultMMKV().encode(key, value)
                is Float -> MMKV.defaultMMKV().encode(key, value)
                is Long -> MMKV.defaultMMKV().encode(key, value)
                is Double -> MMKV.defaultMMKV().encode(key, value)
                is MutableSet<*> -> MMKV.defaultMMKV().encode(key, value as MutableSet<String>)
                is Parcelable -> MMKV.defaultMMKV().encode(key, value)
            }
        } catch (e: Exception) {
            ToastUtils.show("请输入正确的参数")
        }

    }

    /*如果存储的对象,则传入Object.class*/
    fun <T> get(key: String, defaultValue: Any): T {
        return when (defaultValue) {
            is String -> MMKV.defaultMMKV().decodeString(key, defaultValue) as T
            is Int -> MMKV.defaultMMKV().decodeInt(key, defaultValue) as T
            is Boolean -> MMKV.defaultMMKV().decodeBool(key, defaultValue) as T
            is Double -> MMKV.defaultMMKV().decodeDouble(key, defaultValue) as T
            is Float -> MMKV.defaultMMKV().decodeFloat(key, defaultValue) as T
            is Long -> MMKV.defaultMMKV().decodeLong(key, defaultValue) as T
            is Class<*> -> outAny(
                key,
                defaultValue
            )
            else -> throw Exception("请传入正确的参数")
        }
    }

    private fun <T> outAny(key: String, defaultValue: Any): T {
        if (defaultValue is MutableSet<*>) {
            return MMKV.defaultMMKV().decodeStringSet(
                key,
                mutableSetOf()
            ) as T
        }
        return MMKV.defaultMMKV().decodeParcelable(
            key,
            defaultValue as Class<Parcelable>
        ) as T
    }
}