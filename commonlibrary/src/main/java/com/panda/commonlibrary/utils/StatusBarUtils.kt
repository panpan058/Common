package com.panda.commonlibrary.utils

import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

object StatusBarUtils {
    fun steep(activity: AppCompatActivity) {
        ImmersionBar.with(activity)
            .transparentBar()
            .init()
    }
    fun steep(activity: AppCompatActivity,tBId:Int) {
        ImmersionBar.with(activity)
            .titleBar(tBId)
            .init()
    }
}