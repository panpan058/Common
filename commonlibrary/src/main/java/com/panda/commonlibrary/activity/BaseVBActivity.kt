package com.panda.commonlibrary.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.panda.commonlibrary.R
import com.panda.commonlibrary.viewbind.inflateBindingWithGeneric
import com.panda.commonlibrary.view.LoadingDialog
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity
import java.lang.reflect.Field

/**
 * <pre>
 * Created by ppW
 * e-mail : wangpanpan05@163.com
 * time   : 2020/08/04 15:55
 * desc   :
 * version: 1.0   初始化
 * params:
 * <pre>
</pre></pre> */
abstract class BaseVBActivity<VB : ViewBinding> : RxAppCompatActivity() {
    protected var mRootView: FrameLayout? = null
    protected var mContentView: View? = null
    protected var mNetErrorView: View? = null
    protected var vb: VB? = null
    private var mLoadingDialog: LoadingDialog? = null
    protected abstract fun initVB(): VB
    private fun initVB2(): VB {
        return inflateBindingWithGeneric(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRootView = FrameLayout(this)
        vb = initVB()
        mContentView = vb?.root
        mNetErrorView = LayoutInflater.from(this).inflate(R.layout.layout_net_error, null)
        if (isRx) {
            mRootView?.addView(mNetErrorView)
            mRootView?.addView(mContentView)
            setContentView(mRootView)
            setPresenter()
        } else {
            setContentView(mContentView)
        }
        initView()
        initData()
        bindListeners()
    }

    protected open fun setPresenter() {}
    protected open val isRx: Boolean
        protected get() = false

    protected fun bindListeners() {
        mNetErrorView?.setOnClickListener { reQuest() }
    }

    protected fun reQuest() {}
    protected abstract fun initData()
    open fun initView() {
        showContentLayout()
    }

    //    protected void goActivity (@NonNull Class clazz) {
    //        Intent intent = new Intent(this, clazz);
    //        startActivity(intent);
    //    }
    override fun onDestroy() {
        super.onDestroy()
        mRootView = null
        mContentView = null
        mNetErrorView = null
        fixInputMethodManagerLeak(this)
    }

    protected fun showContentLayout() {
        mNetErrorView?.visibility = View.GONE
        mContentView?.visibility = View.VISIBLE
    }

    protected fun showNetErrorLayout() {
        mNetErrorView?.visibility = View.VISIBLE
        mContentView?.visibility = View.GONE
    }

    open fun startLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
            mLoadingDialog?.setOnDismissListener { endLoading() }
        }
        mLoadingDialog?.show()
    }

    open fun endLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog?.cancel()
        }
    }


    companion object {
        fun fixInputMethodManagerLeak(destContext: Context?) {
            if (destContext == null) {
                return
            }
            val imm =
                destContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    ?: return
            val arr =
                arrayOf("mCurRootView", "mServedView", "mNextServedView")
            var f: Field? = null
            var obj_get: Any? = null
            for (i in arr.indices) {
                val param = arr[i]
                try {
                    f = imm.javaClass.getDeclaredField(param)
                    if (f.isAccessible == false) {
                        f.isAccessible = true
                    } // author: sodino mail:sodino@qq.com
                    obj_get = f[imm]
                    if (obj_get != null && obj_get is View) {
                        if (obj_get.context === destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                            f[imm] = null // 置空，破坏掉path to gc节点
                        } else {
                            // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
//                        if (QLog.isColorLevel()) {
//                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
//                        }
                            break
                        }
                    }
                } catch (t: Throwable) {
                    t.printStackTrace()
                }
            }
        }
    }
}