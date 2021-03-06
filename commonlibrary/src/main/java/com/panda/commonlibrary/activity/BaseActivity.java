package com.panda.commonlibrary.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.panda.commonlibrary.R;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.lang.reflect.Field;


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
public abstract class BaseActivity extends RxAppCompatActivity {


    /**
     * @return 布局ID
     */
    @LayoutRes
    protected abstract int initLayoutId();

    protected FrameLayout mRootView;
    protected View mContentView;
    protected View mNetErrorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = new FrameLayout(this);
        mContentView = LayoutInflater.from(this).inflate(initLayoutId(), null);
        mNetErrorView = LayoutInflater.from(this).inflate(R.layout.layout_net_error, null);
        if (isRx()) {
            mRootView.addView(mContentView);
            mRootView.addView(mNetErrorView);
            setContentView(mRootView);
            setPresenter();
        } else {
            setContentView(mContentView);
        }
        initView();
        initData();
        bindListeners();
    }

    protected void setPresenter() {

    }

    protected boolean isRx() {
        return false;
    }

    protected void bindListeners() {
        mNetErrorView.setOnClickListener(v -> reQuest());
    }

    protected void reQuest() {

    }

    protected abstract void initData();

    protected void initView() {
        showContentLayout();
    }

//    protected void goActivity (@NonNull Class clazz) {
//        Intent intent = new Intent(this, clazz);
//        startActivity(intent);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRootView = null;
        mContentView = null;
        mNetErrorView = null;
        fixInputMethodManagerLeak(this);
    }

    protected final void showContentLayout() {
        mNetErrorView.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
    }

    protected final void showNetErrorLayout() {
        mNetErrorView.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
//                        if (QLog.isColorLevel()) {
//                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
//                        }
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
