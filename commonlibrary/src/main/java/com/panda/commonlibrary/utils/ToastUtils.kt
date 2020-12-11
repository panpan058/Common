package com.panda.commonlibrary.utils

import com.coder.zzq.smartshow.toast.SmartToast

object ToastUtils {
    @JvmStatic
    fun show(msg: String?) {
        SmartToast.show(msg)
    }

    @JvmStatic
    fun show(resId: Int) {
        SmartToast.show(resId)
    }

    @JvmStatic
    fun showInCenter(msg: String?) {
        SmartToast.showInCenter(msg)
    }

    @JvmStatic
    fun showInCenter(msg: Int) {
        SmartToast.showInCenter(msg)
    }
}