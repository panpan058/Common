package com.panda.commonlibrary

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.MessageDialogBuilder
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction

/*log日志扩展方法*/
fun e(tag: String = BaseApp.getInstance().packageName, msg: String) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, msg)
    }
}

fun d(tag: String = BuildConfig.LIBRARY_PACKAGE_NAME, msg: String) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, msg)
    }
}

/*沉浸式扩展方法*/
fun AppCompatActivity.steep() {
    ImmersionBar.with(this)
        .transparentBar()
        .init()
}

fun AppCompatActivity.steep(tBId: Int) {
    ImmersionBar.with(this)
        .titleBar(tBId)
        .init()
}

/*跳转页面扩展方法*/
fun AppCompatActivity.goActivity(clazz: Class<out AppCompatActivity>) {
    startActivity(Intent(this, clazz))
}

/*显示对话框的扩展方法*/
fun showCustomDialog(
    context: Context,
    title: String,
    msg: String,
    ensure: () -> Unit,
    cancel: () -> Unit,
    ensureString: String = "确定",
    cancelString: String = "取消"
) {
    MessageDialogBuilder(context)
        .setTitle(title)
        .setMessage(msg)
        .addAction(
            cancelString
        ) { dialog, _ ->
            dialog.dismiss()
            cancel()
        }
        .addAction(
            0,
            ensureString,
            QMUIDialogAction.ACTION_PROP_POSITIVE
        ) { dialog, _ ->
            dialog.dismiss()
            ensure()
        }
        .create(R.style.QMUI_Dialog).show()
}
/*Toast的扩展方法*/
fun t(msg: String) {
    ToastUtils.show(msg)
}
fun t(msgId: Int) {
    ToastUtils.show(msgId)
}