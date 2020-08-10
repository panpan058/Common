package com.panda.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.panda.common.databinding.ItemActivityMainBinding
import com.panda.commonlibrary.adapter.BaseVBAdapter


/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/08/10 16:31
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
class MainActivityAdapter(mData: MutableList<String>) :
    BaseVBAdapter<String, ItemActivityMainBinding>(mData) {
    override fun bindData(viewBinding: ItemActivityMainBinding, item: String) {
        viewBinding.btn.text = item
    }

    override fun onCreateViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemActivityMainBinding {
        return ItemActivityMainBinding.inflate(layoutInflater, parent, false)
    }

}