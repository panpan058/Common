package com.panda.commonlibrary.fragment

import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.panda.commonlibrary.R
import com.panda.commonlibrary.adapter.BaseAdapter
import com.panda.commonlibrary.adapter.BaseVBAdapter
import com.panda.commonlibrary.databinding.LayoutRvBinding

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/09/27 11:45
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
abstract class BaseRvFragment<T, AD : BaseVBAdapter<T, in ViewBinding>> :
    BaseVBFragment<LayoutRvBinding>() {
    var mData = mutableListOf<T>()
    val mAdapter: AD = initAdapter()
    var canRefresh = true
    var canLodeMore = true
    override fun initVB(container: ViewGroup?): LayoutRvBinding {
        return LayoutRvBinding.inflate(layoutInflater, container, false)
    }

    abstract fun <AD> initAdapter(): AD
    abstract fun initLayoutManager(): RecyclerView.LayoutManager
    fun initChildIds(@IdRes vararg ids: Int) {
        mAdapter.addChildClickViewIds(*ids)
    }

    override fun initData() {
        vb?.apply {
            rv.layoutManager = initLayoutManager()
            rv.adapter = mAdapter
            mAdapter.recyclerView = rv
            mAdapter.setEmptyView(initEmptyView())
            srl.setEnableRefresh(canRefresh)
            srl.setEnableLoadMore(canLodeMore)
            srl.setOnRefreshListener {
                onRefresh()
            }
            srl.setOnLoadMoreListener {
                onLoadMore()
            }
            initChildIds()
            mAdapter.setOnItemChildClickListener { _, _, position ->
                onItemChildClick(position)
            }
            mAdapter.setOnItemClickListener { _, _, position ->
                onItemClick(position)
            }
        }

    }

    fun onItemClick(position: Int) {
    }

    fun onItemChildClick(position: Int) {
    }

    fun onLoadMore() {

    }

    fun onRefresh() {

    }

    fun initEmptyView(): Int {
        return R.layout.layout_net_error
    }


}