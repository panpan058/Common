package com.panda.commonlibrary.utils

import com.coder.zzq.smartshow.toast.SmartToast

public object ToastUtils {
    fun show(msg: String?) {
        SmartToast.show(msg)
    }
    fun show(resId: Int) {
        SmartToast.show(resId)
    }
    fun showInCenter(msg: String?) {
        SmartToast.showInCenter(msg)
    }
    fun showInCenter(msg: Int) {
        SmartToast.showInCenter(msg)
    }
}