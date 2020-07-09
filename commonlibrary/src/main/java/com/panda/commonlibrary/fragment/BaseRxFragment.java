package com.panda.commonlibrary.fragment;

import android.content.Context;

import androidx.annotation.NonNull;

import com.panda.commonlibrary.ExtensionsKt;
import com.panda.commonlibrary.mvp.BaseContract;
import com.panda.commonlibrary.mvp.BasePresenter;
import com.panda.commonlibrary.utils.ToastUtils;


/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/03/01
 *     desc   : Fragment基类
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public abstract class BaseRxFragment<P extends BasePresenter> extends BaseFragment implements BaseContract.View {

    protected P mPresenter;

    @NonNull
    protected abstract P initPresenter ();

    @Override
    public void onAttach (@NonNull Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void onError (String msg) {
        ExtensionsKt.t(msg);
    }


    @Override
    public void netError () {
        showNetError();
    }
}
