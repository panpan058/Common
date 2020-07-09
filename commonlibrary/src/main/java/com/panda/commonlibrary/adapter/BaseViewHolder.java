package com.panda.commonlibrary.adapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/06/15 16:26
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
public  class BaseViewHolder< VB extends ViewBinding> extends RecyclerView.ViewHolder {
    public final VB vb;

    public BaseViewHolder(VB vb) {
        super(vb.getRoot());
        this.vb = vb;
    }
}
