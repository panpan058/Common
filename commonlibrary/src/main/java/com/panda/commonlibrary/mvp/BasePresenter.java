package com.panda.commonlibrary.mvp;

import android.content.Context;

import com.panda.commonlibrary.BaseApp;
import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxDialogFragment;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.lang.ref.WeakReference;

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
public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {
    private WeakReference<V> mView;
    private WeakReference<Context> mContext;
    private LifecycleTransformer<Integer> mLifecycleTransformer;

    public BasePresenter () {
    }

    @Override
    public void attachView (V view) {
        mView = new WeakReference<>(view);
        V v = mView.get();
        if (v instanceof RxAppCompatActivity) {
            mContext = new WeakReference<>((RxAppCompatActivity) v);
            mLifecycleTransformer = ((RxAppCompatActivity) v).bindUntilEvent(ActivityEvent.DESTROY);
        } else if (v instanceof RxFragment) {
            mContext = new WeakReference<>(((RxFragment) v).getContext());
            mLifecycleTransformer = ((RxFragment) v).bindUntilEvent(FragmentEvent.DESTROY);
        } else if (v instanceof RxDialogFragment) {
            mContext = new WeakReference<>(((RxDialogFragment) v).getContext());
            mLifecycleTransformer = ((RxDialogFragment) v).bindUntilEvent(FragmentEvent.DESTROY);
        }
    }

    protected LifecycleTransformer getLifecycleTransformer () {
        return mLifecycleTransformer;
    }

    public Context getContext () {
        if (mContext != null) {
            return mContext.get();
        }
        return BaseApp.Companion.getInstance().getApplicationContext();
    }

    @Override
    public void detachView () {
        if (null != mView) {
            mView.clear();
            mView = null;
        }
        if (mContext != null) {
            mContext.clear();
            mContext = null;
        }
    }

    public V getView () {
        return mView.get();
    }
}
