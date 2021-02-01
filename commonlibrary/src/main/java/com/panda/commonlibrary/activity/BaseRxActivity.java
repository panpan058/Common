package com.panda.commonlibrary.activity;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.panda.commonlibrary.mvp.BaseContract;
import com.panda.commonlibrary.mvp.BasePresenter;
import com.panda.commonlibrary.view.LoadingDialog;


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
public abstract class BaseRxActivity<P extends BasePresenter<? super BaseContract.View>> extends BaseActivity implements BaseContract.View {
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

    /**
     * @return 布局ID
     */
    @LayoutRes
    public abstract int initLayoutId();

    /**
     * @return 初始化presenter
     */
    @NonNull
    protected abstract P initPresenter();

    @Override
    protected void setPresenter() {
        mPresenter = initPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected boolean isRx() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void startLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    @Override
    public void endLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
        }
    }

    @Override
    public void onError(String msg) {
    }

    @Override
    public void netError() {
        showNetErrorLayout();
    }
}
