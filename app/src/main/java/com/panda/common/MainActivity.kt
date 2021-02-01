package com.panda.common

import androidx.recyclerview.widget.LinearLayoutManager
import com.panda.common.activity.*
import com.panda.common.adapter.MainActivityAdapter
import com.panda.common.databinding.ActivityMainBinding
import com.panda.commonlibrary.activity.BaseVBActivity
import com.panda.commonlibrary.extension.goActivity
import com.panda.commonlibrary.extension.showCustomDialog
import com.panda.commonlibrary.extension.t

class MainActivity : BaseVBActivity<ActivityMainBinding>() {

    private val mData: MutableList<String> by lazy {
        arrayListOf(
            "权限",
            "qq表情",
            "对话框",
            "输入框",
            "选择图片",
            "WebView优化"
        )
    }
    private val mAdapter: MainActivityAdapter by lazy {
        MainActivityAdapter(mData)
    }

    override fun initVB(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData() {
        vb?.apply {
            rv.layoutManager = LinearLayoutManager(this@MainActivity)
            rv.adapter = mAdapter
            mAdapter.recyclerView = rv
            mAdapter.setOnItemClickListener { adapter, view, position ->
                when (position) {
                    0 -> goActivity<PermissionActivity>()
                    1 -> goActivity<QQFaceActivity>()
                    2 -> showDialog()
                    3 -> goActivity<ChatInputActivity>()
                    4 -> goActivity<ImagePickerActivity>()
                    5 -> goActivity<WebViewActivity>()
                }
            }
        }
    }

    private fun showDialog() {
        showCustomDialog(
            title = "标题",
            msg = "信息",
            ensure = {
                t("确定")
            }
        )
    }

}
