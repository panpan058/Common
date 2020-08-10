package com.panda.commonlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.panda.commonlibrary.R;
import com.trello.rxlifecycle3.components.support.RxFragment;



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
public abstract class BaseFragment extends RxFragment {
    protected final String TAG = this.getClass().getSimpleName();

    protected Context mContext;
    private boolean isPrepared;
    private boolean isFirst = false;
    private boolean isVisible;
    protected View mRootView;
    protected View mLayoutNetError;
    protected View mLayoutContent;

    @LayoutRes
    protected abstract int getLayoutId ();

    protected abstract void initData ();


    @Override
        public void onActivityCreated (@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            isPrepared = true;
        }

        @Override
        public void onAttach (@NonNull Context context) {
            super.onAttach(context);
            mContext = getActivity();
        }

        @Nullable
        @Override
        public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            if (mRootView == null) {
                mRootView = inflater.inflate(R.layout.layout_fragment, container, false);
                FrameLayout frameLayout = mRootView.findViewById(R.id.fl_layoutFragment);
                mLayoutContent = inflater.inflate(getLayoutId(), null);
                mLayoutNetError = inflater.inflate(R.layout.layout_net_error, null);
                frameLayout.addView(mLayoutContent);
                frameLayout.addView(mLayoutNetError);
            }
            showContent();
            return mRootView;
        }

        @Override
        public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            isFirst = true;
        }

        @Override
        public void setUserVisibleHint (boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);
            if (getUserVisibleHint() && isFirst && isPrepared) {
                isVisible = true;
                isFirst = false;
                lazyLoad();
            } else {
                isVisible = false;
            }
        }

        private void lazyLoad () {
            if (isVisible) {
                initData();
            }
        }

        @Override
        public void onResume () {
            super.onResume();
            setUserVisibleHint(getUserVisibleHint());
        }

        @Override
        public void onDestroy () {
            super.onDestroy();
            isPrepared = false;
            mContext = null;
            mRootView = null;
            mLayoutNetError = null;
            mLayoutContent = null;
        }

        protected void showContent () {
            mLayoutContent.setVisibility(View.VISIBLE);
            mLayoutNetError.setVisibility(View.GONE);
        }

        protected void showNetError () {
            mLayoutContent.setVisibility(View.GONE);
            mLayoutNetError.setVisibility(View.VISIBLE);
    }
}
