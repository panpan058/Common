package com.panda.common.activity

import android.Manifest
import com.panda.common.databinding.ActivityPermissionBinding
import com.panda.commonlibrary.activity.BaseVBActivity
import com.panda.commonlibrary.extension.permission
import com.panda.commonlibrary.extension.t

class PermissionActivity : BaseVBActivity<ActivityPermissionBinding>() {


    override fun initVB(): ActivityPermissionBinding {
        return ActivityPermissionBinding.inflate(layoutInflater)
    }

    override fun initData() {
        vb?.btnRequestPermission?.setOnClickListener {
            permission(
                this,
                hasPermission = {
                    t("获取了全部权限")
                },
                noPermission = {
                    t("拒绝了权限$it")
                },
                permissions = *arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}

