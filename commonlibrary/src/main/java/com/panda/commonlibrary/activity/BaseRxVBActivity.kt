package com.panda.commonlibrary.activity

import android.content.DialogInterface
import androidx.viewbinding.ViewBinding
import com.panda.commonlibrary.R
import com.panda.commonlibrary.mvp.BaseContract
import com.panda.commonlibrary.mvp.BasePresenter
import com.panda.commonlibrary.utils.ToastUtils.show
import com.panda.commonlibrary.view.LoadingDialog

/**
 * <pre>
 * Created by ppW
 * e-mail : wangpanpan05@163.com
 * time   : 2020/08/04 10:47
 * desc   :
 * version: 1.0   初始化
 * params:
 * <pre>
</pre></pre> */
abstract class BaseRxVBActivity<P : BasePresenter<in BaseContract.View>?, VB : ViewBinding?> :
    BaseVBActivity<VB>(), BaseContract.View {
    protected var mPresenter: P? = null
    private var mLoadingDialog: LoadingDialog? = null

    /**
     * @return 初始化presenter
     */
    protected abstract fun initPresenter(): P
    override fun setPresenter() {
        mPresenter = initPresenter()
        mPresenter?.attachView(this)
    }

    override val isRx: Boolean
        protected get() = true

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun startLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
            mLoadingDialog?.setOnDismissListener { dialog: DialogInterface? -> endLoading() }
        }
        mLoadingDialog?.show()
    }

    override fun endLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.cancel()
        }
    }

    override fun onError(msg: String) {
        show(msg)
    }

    override fun netError() {
        show(R.string.netError)
    }
}