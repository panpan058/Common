package com.panda.commonlibrary.net.model;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class  BaseObservable<T> extends Observable<BaseResponseBody<T>> {
    @Override
    protected void subscribeActual(Observer<? super BaseResponseBody<T>> observer) {

    }
}
