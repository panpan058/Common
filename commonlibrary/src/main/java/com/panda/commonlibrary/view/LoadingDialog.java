package com.panda.commonlibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.panda.commonlibrary.R;


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

public class LoadingDialog extends Dialog {
    private ImageView mImageView;
    private AnimationDrawable mAnimationDrawable;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mImageView = findViewById(R.id.iv_layoutLoading);
        mAnimationDrawable = ((AnimationDrawable) mImageView.getDrawable());
    }

    @Override
    protected void onStart () {
        super.onStart();
        mAnimationDrawable.start();
    }

    @Override
    protected void onStop () {
        super.onStop();
        mAnimationDrawable.stop();
    }

}
