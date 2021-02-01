package com.panda.commonlibrary.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.coder.zzq.smartshow.dialog.EnsureDialog
import com.gyf.immersionbar.ImmersionBar
import com.jiongbull.jlog.JLog
import com.panda.commonlibrary.BaseApp
import com.panda.commonlibrary.BuildConfig
import com.panda.commonlibrary.utils.ToastUtils
import com.permissionx.guolindev.PermissionX
import java.io.Serializable

/*log日志扩展方法*/
fun e(tag: String = BaseApp.getInstance().packageName, msg: String) {
    if (BuildConfig.DEBUG) {
       JLog.e(msg)
    }
}

fun d(tag: String = BuildConfig.LIBRARY_PACKAGE_NAME, msg: String) {
    if (BuildConfig.DEBUG) {
        JLog.json(msg)
    }
}

/*沉浸式扩展方法*/
fun AppCompatActivity.steep() {
    ImmersionBar.with(this)
        .transparentBar()
        .init()
}

fun AppCompatActivity.steepDark() {
    ImmersionBar.with(this).fullScreen(true).statusBarDarkFont(true).init()
}

fun Fragment.steepDark() {
    ImmersionBar.with(this).fullScreen(true).statusBarDarkFont(true).init()
}

fun Fragment.steep() {
    ImmersionBar.with(this).transparentBar().fullScreen(true).init()
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

/*跳转页面扩展方法*/
fun AppCompatActivity.goActivity(
    clazz: Class<out AppCompatActivity>,
    vararg pair: Pair<String, Parcelable>
) {

    var intent = Intent(this, clazz)
    pair.forEach {
        intent.putExtra(it.first, it.second)
    }
    startActivity(intent)
}

/*跳转页面扩展方法*/
fun Fragment.goActivity(clazz: Class<out AppCompatActivity>) {
    startActivity(Intent(context, clazz))
}

fun Fragment.goActivity(
    clazz: Class<out AppCompatActivity>,
    vararg pair: Pair<String, Parcelable>
) {

    var intent = Intent(context, clazz)
    pair.forEach {
        intent.putExtra(it.first, it.second)
    }
    startActivity(intent)
}

inline fun <reified AC : AppCompatActivity> Fragment.goActivity(vararg pair: Pair<String, Parcelable>) {
    var intent = Intent(
        context,
        ktxClass<AC>()
    )
    pair.forEach {
        intent.putExtra(it.first, it.second)
    }
    startActivity(intent)
}

inline fun <reified AC : AppCompatActivity> AppCompatActivity.goActivity(vararg pair: Pair<String, Parcelable>) {
    var intent = Intent(this, ktxClass<AC>())
    pair.forEach {
        intent.putExtra(it.first, it.second)
    }
    startActivity(intent)
}

inline fun <reified AC : AppCompatActivity> Fragment.goActivity() {
    startActivity(
        Intent(
            activity,
            ktxClass<AC>()
        )
    )
}

inline fun <reified AC : AppCompatActivity> AppCompatActivity.goActivity() {
    startActivity(Intent(this, ktxClass<AC>()))
}


/*显示对话框的扩展方法*/
fun AppCompatActivity.showCustomDialog(
    title: String,
    msg: String,
    ensure: () -> Unit,
    cancel: () -> Unit = {},
    ensureString: String = "确定",
    cancelString: String = "取消"

) {
    val dialog = EnsureDialog()
        .cancelBtn(
            cancelString
        )
        .confirmBtn(
            ensureString
        ) { d, type, _ ->
            d.dismiss()
            if (type == 0) {
                ensure()
            } else {
                cancel()
            }
        }
        .message(msg)
        .title(title)
    dialog.showInActivity(this)
}


/*Toast的扩展方法*/
fun t(msg: String) {
    ToastUtils.show(msg)
}

fun t(msgId: Int) {
    ToastUtils.show(msgId)
}

inline fun <reified T> ktxClass() = T::class.java

/*参数化*/
fun Array<out Pair<String, Any>>.toParams(): HashMap<String, Any> {
    val it = this
    return hashMapOf<String, Any>().apply {
        putAll(it)
    }
}

fun permission(
    activity: AppCompatActivity?,
    hasPermission: () -> Unit,
    noPermission: (MutableList<String>) -> Unit,
    vararg permissions: String?
) {
    PermissionX.init(activity)
        .permissions(*permissions)
        .explainReasonBeforeRequest()
        .onExplainRequestReason { scope, deniedList ->
            scope.showRequestReasonDialog(deniedList, "即将重新申请的权限是程序必须依赖的权限", "我已明白", "取消")
        }.onForwardToSettings { scope, deniedList ->
            scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白", "取消")
        }
        .request { allGranted, _, deniedList ->
            if (allGranted) {
                hasPermission()
            } else {
                noPermission(deniedList)
            }
        }
}

inline fun <reified T : Activity> Activity.goActivity(vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    if (params.isNotEmpty()) {
        fillIntentArguments(intent, *params)
    }
    startActivity(intent)
}

fun fillIntentArguments(intent: Intent, vararg params: Pair<String, Any?>) {
    params.forEach {
        val value = it.second
        when (value) {
            null -> intent.putExtra(it.first, null as Serializable?)
            is Int -> intent.putExtra(it.first, value)
            is Long -> intent.putExtra(it.first, value)
            is CharSequence -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is Float -> intent.putExtra(it.first, value)
            is Double -> intent.putExtra(it.first, value)
            is Char -> intent.putExtra(it.first, value)
            is Short -> intent.putExtra(it.first, value)
            is Boolean -> intent.putExtra(it.first, value)
            is Serializable -> intent.putExtra(it.first, value)
            is Bundle -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> intent.putExtra(it.first, value)
            is LongArray -> intent.putExtra(it.first, value)
            is FloatArray -> intent.putExtra(it.first, value)
            is DoubleArray -> intent.putExtra(it.first, value)
            is CharArray -> intent.putExtra(it.first, value)
            is ShortArray -> intent.putExtra(it.first, value)
            is BooleanArray -> intent.putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}
