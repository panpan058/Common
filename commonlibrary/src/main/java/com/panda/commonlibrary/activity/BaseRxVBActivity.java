package com.panda.commonlibrary.activity;

import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.panda.commonlibrary.R;
import com.panda.commonlibrary.mvp.BaseContract;
import com.panda.commonlibrary.mvp.BasePresenter;
import com.panda.commonlibrary.utils.ToastUtils;
import com.panda.commonlibrary.view.LoadingDialog;


/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/08/04 10:47
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public abstract class BaseRxVBActivity<P extends BasePresenter, VB extends ViewBinding> extends BaseVBActivity<VB> implements BaseContract.View {
    protected P mPresenter;
    private LoadingDialog mLoadingDialog;

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
            mLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    endLoading();
                }
            });
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
        ToastUtils.INSTANCE.show(msg);
    }

    @Override
    public void netError() {
        ToastUtils.INSTANCE.show(R.string.netError);
    }
}
