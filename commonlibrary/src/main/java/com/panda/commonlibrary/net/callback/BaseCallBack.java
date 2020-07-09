package com.panda.commonlibrary.net.callback;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.panda.commonlibrary.net.HttpConfig;
import com.panda.commonlibrary.net.exception.BaseException;
import com.panda.commonlibrary.net.exception.TokenInvalidException;
import com.panda.commonlibrary.net.model.BaseResponseBody;
import com.panda.commonlibrary.utils.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2019/02/25
 *     desc   : 回调封装基础类
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */

public abstract class BaseCallBack<T> implements Observer<BaseResponseBody<T>> {

    private Context mContext;
    private Disposable mDisposable;

    protected BaseCallBack (Context mContext) {
        this.mContext = mContext;
    }

    protected BaseCallBack () {
//        this.mContext = BaseApplication.getInstance().getApplicationContext();
    }


    @Override
    public void onSubscribe (Disposable d) {
        mDisposable = d;
        startLoading();
    }

    @Override
    public void onNext (BaseResponseBody<T> rBaseResponseBody) {
        if (HttpConfig.CODE_SUCCESS.equals(rBaseResponseBody.getCode())) {
            onSuccess(rBaseResponseBody.getData());
        } else if (HttpConfig.CODE_TOKEN_INVALID.equals(rBaseResponseBody.getCode())) {
            //token过期的处理
            onError(new TokenInvalidException());
        } else {
            onError(new BaseException());
        }
    }

    @Override
    public void onError (Throwable e) {
        if (e instanceof HttpException) {
            //http错误
            onError(e.getMessage());
        } else if (e instanceof ConnectException || e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            onNetError();
            //连接错误
        } else if (e instanceof InterruptedIOException) {
            //超时
            onError(e.getMessage());
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            onError(e.getMessage());
        } else {
            //其他错误
            onError(e.getMessage());
        }
        endLoading();
        Log.e("baseCallback", "onError: " + e.toString());

    }

    @Override
    public void onComplete () {
        endLoading();
    }

    //成功回调
    protected abstract void onSuccess (T data);

    //失败回调
    protected void onError (String errorMsg) {
        ToastUtils.INSTANCE.show(errorMsg);
    }

    //网络错误回调
    protected void onNetError () {

    }

    //显示加载框
    public void startLoading () {
//        if (mLoadingDialog == null) {
//            if (mContext != null) {
//                mLoadingDialog = new LoadingDialog(mContext);
//                mLoadingDialog.setOnDismissListener(dialog -> {
//                    if (mDisposable != null && ! mDisposable.isDisposed()) {
//                        mDisposable.dispose();
//                    }
//                });
//            }
//        }
//        assert mLoadingDialog != null;
//        if (! mLoadingDialog.isShowing()) {
//            mLoadingDialog.show();
//        }
    }

    //取消加载框
    public void endLoading () {
//        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
//            mLoadingDialog.cancel();
//            mLoadingDialog = null;
//            mContext = null;
//        }
        if (mDisposable != null && ! mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
