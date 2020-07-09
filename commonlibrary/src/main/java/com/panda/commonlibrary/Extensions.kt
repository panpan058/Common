package com.panda.commonlibrary

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.panda.commonlibrary.utils.ToastUtils
import com.qmuiteam.qmui.widget.dialog.QMUIDialog.MessageDialogBuilder
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction
import java.lang.Exception
import java.lang.reflect.ParameterizedType

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

inline fun <reified AC : AppCompatActivity> AppCompatActivity.goActivity() {
    startActivity(Intent(this, ktxClass<AC>()))

}

inline fun <reified VM : ViewModel> AppCompatActivity.initVM(): Class<VM> {
    return ktxClass()
}

inline fun <reified VB : ViewBinding> AppCompatActivity.initVB(): Class<VB> {
    return ktxClass()
}

fun <T> Any.toClass(): Class<T>? {
    try {
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            return superClass.actualTypeArguments[0] as Class<T>
        }
    } catch (ex: Exception) {
        return null
    }
    return null
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

inline fun <reified T> ktxClass() = T::class.java