package com.panda.commonlibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/08/04 15:43
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
abstract class BaseVBAdapter<T, VB : ViewBinding>(data: MutableList<T>) :
    BaseQuickAdapter<T, BaseVBAdapter.BaseVHVB<VB>>(
        0, data) {
    class BaseVHVB<VB : ViewBinding>(val viewBinding: VB) : BaseViewHolder(viewBinding.root)

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BaseVHVB<VB> {
        return BaseVHVB(onCreateViewBinding(LayoutInflater.from(parent.context), parent, viewType))
    }
    override fun convert(holder: BaseVHVB<VB>, item: T) {
        bindData(holder.viewBinding, item)
    }

    abstract fun bindData(viewBinding: VB, item: T)

    abstract fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): VB
}