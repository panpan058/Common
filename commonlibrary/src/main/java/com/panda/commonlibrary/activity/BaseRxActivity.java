package com.panda.commonlibrary.activity;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.panda.commonlibrary.mvp.BaseContract;
import com.panda.commonlibrary.mvp.BasePresenter;


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
public abstract class BaseRxActivity<P extends BasePresenter> extends BaseActivity implements BaseContract.View {
    protected P mPresenter;

    /**
     * @return 布局ID
     */
    @LayoutRes
    protected abstract int initLayoutId ();

    /**
     * @return 初始化presenter
     */
    @NonNull
    protected abstract P initPresenter ();

    @Override
    protected void setPresenter () {
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected boolean isRx () {
        return true;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void onError (String msg) {
    }

    @Override
    public void netError () {
        showNetErrorLayout();
    }
}
