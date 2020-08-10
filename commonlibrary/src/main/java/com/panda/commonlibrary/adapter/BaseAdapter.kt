package com.panda.commonlibrary.adapter;

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * <pre>
 * Created by ppW
 * e-mail : wangpanpan05@163.com
 * time   : 2020/06/15 16:23
 * desc   :
 * version: 1.0   初始化
 * params:
 * <pre>
</pre></pre> */
abstract class BaseAdapter<T, VB : ViewBinding?>(private val mData: List<T>?) :
    RecyclerView.Adapter<BaseViewHolder<VB>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val genericSuperclass = javaClass.genericSuperclass
        if (genericSuperclass is ParameterizedType) {
            val actualTypeArguments =
                genericSuperclass.actualTypeArguments
            try {
                val method =
                    (actualTypeArguments[1] as Class<VB>).getMethod(
                        "inflate",
                        LayoutInflater::class.java
                    )
                val invoke =
                    method.invoke(null, LayoutInflater.from(parent.context))
                return BaseViewHolder(invoke as VB)
            } catch (e: Exception) {
                Log.e("初始化错误", "ViewHolder初始化错误")
            }
        }
        throw java.lang.Exception()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bindData(holder.vb, mData!![position])
    }

    abstract fun bindData(holder: VB, item: T)
    override fun getItemCount(): Int {
        return mData?.size ?: 0
    }

}