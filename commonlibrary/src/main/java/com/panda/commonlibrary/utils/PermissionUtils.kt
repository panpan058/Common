package com.panda.commonlibrary.utils

import androidx.appcompat.app.AppCompatActivity
import com.permissionx.guolindev.PermissionX

object PermissionUtils {
    fun request(
        activity: AppCompatActivity?,
        callback: PandaCallback?,
        vararg permissions: String?
    ) {
        PermissionX.init(activity)
            .permissions(*permissions)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "即将重新申请的权限是程序必须依赖的权限", "我已明白", "取消")
            }.onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList,"您需要去应用程序设置当中手动开启权限", "我已明白", "取消")
            }
            .request { allGranted, _, deniedList ->
                if (allGranted) {
                    callback?.hasPermission()
                } else {
                    callback?.noPermission(deniedList)
                }
            }
    }

    interface PandaCallback {

        fun noPermission(deniedList: MutableList<String>)
        fun hasPermission()
    }
}