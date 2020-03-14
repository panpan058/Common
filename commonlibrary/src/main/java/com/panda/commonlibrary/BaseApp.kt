package com.panda.commonlibrary

import android.app.Application
import com.coder.zzq.smartshow.core.SmartShow
import com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler
import com.tencent.mmkv.MMKV

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        baseApp = this
        SmartShow.init(this)
        MMKV.initialize(this)
        AdaptationUtils.initAppDensity(this)
        QMUIQQFaceCompiler.setDefaultQQFaceManager(QDQQFaceManager.getInstance())
    }

    companion object {
        private lateinit var baseApp: BaseApp
        fun getInstance(): BaseApp {
            return baseApp
        }
    }
}