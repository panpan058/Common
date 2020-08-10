package com.panda.commonlibrary.fragment;

import android.content.Context;

import androidx.annotation.NonNull;

import com.panda.commonlibrary.mvp.BaseContract;
import com.panda.commonlibrary.mvp.BasePresenter;
import com.panda.commonlibrary.view.LoadingDialog;


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
    private LoadingDialog mDialog;

    @NonNull
    protected abstract P initPresenter();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    @Override
    public void startLoading() {
        if (mDialog == null) {
            mDialog = new LoadingDialog(mContext);
        }
        mDialog.show();
    }

    @Override
    public void endLoading() {
        if (mDialog != null) {
            mDialog.cancel();
        }
    }
}
