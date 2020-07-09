package com.panda.common

import androidx.multidex.MultiDex
import com.panda.commonlibrary.BaseApp

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/07/09 16:54
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
class APP : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}