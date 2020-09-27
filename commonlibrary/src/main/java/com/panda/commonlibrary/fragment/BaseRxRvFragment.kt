package com.panda.commonlibrary.fragment

import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.panda.commonlibrary.R
import com.panda.commonlibrary.databinding.LayoutRvBinding
import com.panda.commonlibrary.mvp.BaseContract
import com.panda.commonlibrary.mvp.BasePresenter

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/09/27 11:58
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
abstract class BaseRxRvFragment<P : BasePresenter<BaseContract.View>, T, D : BaseQuickAdapter<T, in BaseViewHolder>>
    : BaseRxVBFragment<P, LayoutRvBinding>() {
    var mData: MutableList<T> = arrayListOf()
    val mAdapter: D by lazy {
        initAdapter()
    }

    abstract fun initAdapter(): D
    abstract fun initLayoutManager(): RecyclerView.LayoutManager

    var canRefresh = true
    var canLodeMore = true
    override fun initVB(): LayoutRvBinding {
        return LayoutRvBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        initTopBar()
    }

    private fun initTopBar() {
    }

    override fun initData() {
        vb?.apply {
            rv.layoutManager = initLayoutManager()
            rv.adapter = mAdapter
            mAdapter.recyclerView = rv
            mAdapter.setEmptyView(R.layout.layout_no_data)
            initChildIds()
            mAdapter.setOnItemChildClickListener { _, _, position ->
                onItemChildClick(position)
            }
            mAdapter.setOnItemClickListener { _, _, position ->
                onItemClick(position)
            }
            srl.setEnableRefresh(canRefresh)
            srl.setEnableLoadMore(canLodeMore)
            srl.setOnRefreshListener {
                onRefresh()
            }
            srl.setOnLoadMoreListener {
                onLoadMore()
            }
        }
    }

    fun initChildIds(@IdRes vararg ids: Int) {
        mAdapter.addChildClickViewIds(*ids)
    }

    fun onLoadMore() {

    }

    fun onRefresh() {
    }

    override fun finishRefreshLoadMore() {
        vb?.srl?.finishRefresh()
        vb?.srl?.finishLoadMore()
    }

    fun onItemClick(position: Int) {

    }

    fun onItemChildClick(position: Int) {
    }

}