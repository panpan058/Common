package com.panda.common

import android.Manifest
import android.os.Bundle
import com.panda.commonlibrary.PermissionUtils
import com.panda.commonlibrary.ToastUtils
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : BaseActivity(),
    PermissionUtils.PandaCallback{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)
        btn_requestPermission.setOnClickListener {
            PermissionUtils.request(this,this,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
        }
    }

    override fun noPermission() {
        ToastUtils.show("拒绝了权限")
    }

    override fun doNotAsk() {
        ToastUtils.show("勾选了不再询问")
    }

    override fun hasPermission() {
        ToastUtils.show("获取了全部权限")
    }
}

