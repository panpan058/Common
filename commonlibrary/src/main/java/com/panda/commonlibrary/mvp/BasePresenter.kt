package com.panda.commonlibrary.mvp

import android.content.Context
import com.panda.commonlibrary.BaseApp.Companion.getInstance
import com.panda.commonlibrary.net.callback.BaseCallBack
import com.panda.commonlibrary.net.model.BaseResponseBody
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import com.trello.rxlifecycle3.components.support.RxDialogFragment
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

/**
 * <pre>
 * Created by ppW
 * e-mail : wangpanpan05@163.com
 * time   : 2019/02/25
 * desc   :
 * version: 1.0   初始化
 * params:
 * <pre>
</pre></pre> */
open class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {
    private lateinit var mView: WeakReference<V>
    private var mContext: WeakReference<Context?>? = null

    override fun attachView(view: V) {
        mView = WeakReference(view)
    }

    val context: Context?
        get() = if (mContext != null) {
            mContext!!.get()
        } else getInstance().applicationContext

    override fun detachView() {
        mView.clear()
        if (mContext != null) {
            mContext!!.clear()
            mContext = null
        }
    }

    private fun <L> getLife(): LifecycleTransformer<L> {
        return when (val v = mView.get()) {
            is RxAppCompatActivity -> {
                mContext =
                    WeakReference(v as RxAppCompatActivity?)
                (v as RxAppCompatActivity).bindUntilEvent(ActivityEvent.DESTROY)
            }
            is RxFragment -> {
                mContext =
                    WeakReference((v as RxFragment).context)
                (v as RxFragment).bindUntilEvent(
                    FragmentEvent.DESTROY
                )
            }
            is RxDialogFragment -> {
                mContext =
                    WeakReference((v as RxDialogFragment).context)
                (v as RxDialogFragment).bindUntilEvent(
                    FragmentEvent.DESTROY
                )
            }
            else -> {
                throw Exception("mView只能绑定RxAppCompatActivity,RxFragment,RxDialogFragment三者之一")
            }
        }
    }

    val view: V?
        get() = mView.get()

    fun <T> postData(
        api: Observable<BaseResponseBody<T>>,
        apiSuccess: (T) -> Unit
    ) {
        api.subscribeOn(Schedulers.io())
            .compose(getLife())
            .map {

                return@map it
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseCallBack<T>() {
                override fun onSuccess(data: T) {
                    apiSuccess.invoke(data)
                }

                override fun startLoading() {
                    view?.startLoading()
                }

                override fun endLoading() {
                    view?.endLoading()
                }

                override fun onError(errorMsg: String?) {
                    view?.onError(errorMsg)
                }

                override fun onNetError() {
                    view?.netError()
                }
            })

    }
}