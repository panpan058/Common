package com.panda.commonlibrary.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/07/08 16:36
 *     desc   : 带有viewBinding 和ViewModel的BaseActivity
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
open abstract class BaseActivityVM<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {
    lateinit var model: VM
    lateinit var vb: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVMAndVB()
        initData()
    }

    protected abstract fun initData()
    private fun initVMAndVB() {
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            model = ViewModelProvider(this)[superClass.actualTypeArguments[0] as Class<VM>]
            val clazz = superClass.actualTypeArguments[1] as Class<ViewBinding>
            vb = clazz.getMethod("inflate", LayoutInflater::class.java)
                .invoke(null, layoutInflater) as VB
            setContentView(vb.root)
        }
    }
}