package com.panda.commonlibrary.extension

import com.panda.commonlibrary.net.HttpConfig
import com.panda.commonlibrary.net.callback.BaseCallBack
import com.panda.commonlibrary.net.callback.BaseObservable
import com.panda.commonlibrary.net.model.BaseResponseBody
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.lang.Exception


/**
 *   <pre>
 *   author: pandaWang
 *   time: 2020/12/2 15:43
 *   version: v1.0
 *   des:   rxJava 请求的扩展类
 *   <pre>
 */
fun <T> Observable<BaseResponseBody<T>>.post(baseCallBack: BaseCallBack<T>) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(baseCallBack)
}

fun <T, R> Observable<BaseResponseBody<T>>.withObservable(
    firstCallBack: (T) -> Unit = {},
    observable: Observable<BaseResponseBody<R>>,
    baseCallBack: BaseCallBack<R>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .flatMap(Function<BaseResponseBody<T>, ObservableSource<BaseResponseBody<R>?>> { response: BaseResponseBody<T> ->
            if (response.code == HttpConfig.CODE_SUCCESS) {
                firstCallBack.invoke(response.data)
                return@Function observable.subscribeOn(Schedulers.io())
            }
            return@Function Observable.error<BaseResponseBody<R>>(Exception(response.msg))
        })
        .subscribe(baseCallBack)
}

