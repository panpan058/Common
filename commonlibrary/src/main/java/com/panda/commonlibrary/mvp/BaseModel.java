package com.panda.commonlibrary.mvp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.panda.commonlibrary.BaseApp;


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
public class BaseModel<V extends BaseContract.View> {
    private V mView;
    private Context mContext;

    public V getView () {
        return mView;
    }

    public Context getContext () {
        return mContext;
    }

    public void setView (V view) {
        mView = view;
        if (mView instanceof AppCompatActivity) {
            mContext = ((AppCompatActivity) mView);
        } else if (mView instanceof Fragment) {
            mContext = ((Fragment) mView).getContext();
        } else {
            mContext = BaseApp.Companion.getInstance().getApplicationContext();
        }
    }
}
