package com.panda.commonlibrary.fragment;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.panda.commonlibrary.R
import com.panda.commonlibrary.view.LoadingDialog
import com.trello.rxlifecycle3.components.support.RxFragment

abstract class BaseVBFragment<VB : ViewBinding> : RxFragment() {
    private var mDialog: LoadingDialog? = null
    protected var mRootView: View? = null
    protected var mLayoutNetError: View? = null
    protected var mLayoutContent: View? = null
    private var mContext: Context? = null
    lateinit var vb: VB
    private var isLoaded = false

    protected abstract fun initVB(container: ViewGroup?): VB

    protected abstract fun initData()

    /**
     * 是否每次都加载刷新
     *
     * @return 默认false
     */
    protected open fun isRefresh(): Boolean {
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.layout_fragment, container, false)
            val frameLayout =
                mRootView!!.findViewById<FrameLayout>(R.id.fl_layoutFragment)
            mLayoutNetError = inflater.inflate(R.layout.layout_net_error, null)
            vb = initVB(container)
            mLayoutContent = vb.root
            frameLayout.addView(mLayoutContent)
            frameLayout.addView(mLayoutNetError)
        }
        showContent()
        return mRootView
    }

    override fun onResume() {
        super.onResume()
        if (isRefresh()) {
            initData()
        } else {
            if (!isLoaded) initData()
        }
        isLoaded = true
    }


    protected open fun showContent() {
        mLayoutContent!!.visibility = View.VISIBLE
        mLayoutNetError!!.visibility = View.GONE
    }

    protected open fun showNetError() {
        mLayoutContent!!.visibility = View.GONE
        mLayoutNetError!!.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = activity
    }

    override fun onDestroy() {
        super.onDestroy()
        mContext = null
        mRootView = null
        mLayoutNetError = null
        mLayoutContent = null
    }

}
