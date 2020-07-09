package com.panda.commonlibrary

import android.app.Application
import android.content.Context
import com.coder.zzq.smartshow.core.SmartShow
import com.panda.commonlibrary.emoji.QDQQFaceManager
import com.panda.commonlibrary.utils.AdaptationUtils
import com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler
import com.tencent.mmkv.MMKV
import xcrash.ICrashCallback
import xcrash.XCrash

open class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        baseApp = this
        SmartShow.init(this)
        MMKV.initialize(this)
        AdaptationUtils.initAppDensity(this)
        QMUIQQFaceCompiler.setDefaultQQFaceManager(QDQQFaceManager.getInstance())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initXCrash()

    }

    private fun initXCrash() {
        val params = XCrash.InitParameters()
        params.setJavaCallback { logPath, emergency ->
            e( msg = "崩溃路径logPatch={$logPath}")
        }
        XCrash.init(this)
    }

    companion object {
        private lateinit var baseApp: BaseApp
        fun getInstance(): BaseApp {
            return baseApp
        }
    }
}