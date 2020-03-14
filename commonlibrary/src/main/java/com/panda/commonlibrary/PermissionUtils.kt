package com.panda.commonlibrary

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ppw.permission.PPWPermissions
import com.ppw.permission.PermissionCallback

public object PermissionUtils {
    fun request(
        activity: AppCompatActivity?,
        callback: PandaCallback?,
        vararg permissions: String?
    ) {
        PPWPermissions.getInstance()
            .with(activity)
            .permissions(*permissions)
            .request(object :PermissionCallback{
                override fun noPermission() {
                    callback?.noPermission()
                }

                override fun doNotAsk() {
                    callback?.doNotAsk()
                }

                override fun hasPermission() {
                    callback?.hasPermission()
                }

            })
    }

    fun request(
        fragment: Fragment?,
        callback: PandaCallback?,
        vararg permissions: String?
    ) {
        PPWPermissions.getInstance()
            .with(fragment)
            .permissions(*permissions)
            .request(object :PermissionCallback{
                override fun noPermission() {
                    callback?.noPermission()
                }

                override fun doNotAsk() {
                    callback?.doNotAsk()
                }

                override fun hasPermission() {
                    callback?.hasPermission()
                }

            })
    }
     interface PandaCallback {
          fun doNotAsk() {
         }

          fun noPermission() {
         }

          fun hasPermission() {
         }
     }
}