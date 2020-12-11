package com.panda.commonlibrary.net.callback;

import com.panda.commonlibrary.net.model.BaseResponseBody;

import io.reactivex.Observable;

/**
 * <pre>
 *   author: pandaWang
 *   time: 2020/12/2 15:56
 *   version: v1.0
 *   des:
 *   <pre>
 */
public abstract class BaseObservable<T> extends Observable<BaseResponseBody<T>> {
}
