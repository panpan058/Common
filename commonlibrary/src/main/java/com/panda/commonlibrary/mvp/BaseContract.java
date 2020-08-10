package com.panda.commonlibrary.mvp;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/02/25
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public interface BaseContract {
    interface View {
        void startLoading();

        void endLoading();

        void onError(String msg);

        void netError();
    }

    interface Presenter<V extends View> {
        void attachView(V view);

        void detachView();
    }
}
