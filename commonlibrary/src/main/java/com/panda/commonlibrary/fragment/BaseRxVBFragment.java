package com.panda.commonlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.panda.commonlibrary.R;
import com.panda.commonlibrary.mvp.BaseContract;
import com.panda.commonlibrary.mvp.BasePresenter;
import com.panda.commonlibrary.utils.ToastUtils;
import com.panda.commonlibrary.view.LoadingDialog;
import com.trello.rxlifecycle3.components.support.RxFragment;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/08/05 17:39
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public abstract class BaseRxVBFragment<P extends BasePresenter, VB extends ViewBinding>
        extends RxFragment
        implements BaseContract.View {
    protected P mPresenter;
    private LoadingDialog mDialog;
    protected View mRootView;
    protected View mLayoutNetError;
    protected View mLayoutContent;
    private Context mContext;
    protected VB vb;
    private boolean isLoaded;

    protected abstract VB initVB();

    protected abstract void initData();

    /**
     * 是否每次都加载刷新
     *
     * @return 默认false
     */
    protected boolean isRefresh() {
        return false;
    }

    @NonNull
    protected abstract P initPresenter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.layout_fragment, container, false);
            FrameLayout frameLayout = mRootView.findViewById(R.id.fl_layoutFragment);
            mLayoutNetError = inflater.inflate(R.layout.layout_net_error, null);
            vb = initVB();
            mLayoutContent = vb.getRoot();
            frameLayout.addView(mLayoutContent);
            frameLayout.addView(mLayoutNetError);
        }
        showContent();
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected void initView() {

    }


    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh()) {
            initData();
            initListeners();
        } else {
            if (!isLoaded) {
                initData();
                initListeners();
            }
        }
        isLoaded = true;
    }

    protected  void initListeners(){};


    protected void showContent() {
        mLayoutContent.setVisibility(View.VISIBLE);
        mLayoutNetError.setVisibility(View.GONE);
    }

    protected void showNetError() {
        mLayoutContent.setVisibility(View.GONE);
        mLayoutNetError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
        mPresenter.attachView(this);
        mContext = getActivity();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
        mRootView = null;
        mLayoutNetError = null;
        mLayoutContent = null;
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


    @Override
    public void onError(String msg) {
        ToastUtils.INSTANCE.show(msg);
        finishRefreshLoadMore();
    }

    protected void finishRefreshLoadMore() {

    }

    @Override
    public void netError() {
        finishRefreshLoadMore();
        ToastUtils.INSTANCE.show(R.string.netError);
    }
}
