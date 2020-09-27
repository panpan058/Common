package com.panda.common.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.panda.common.databinding.ActivityWebviewBinding
import com.panda.commonlibrary.activity.BaseVBActivity

class WebViewActivity : BaseVBActivity<ActivityWebviewBinding>() {
    override fun initVB(): ActivityWebviewBinding {
        return ActivityWebviewBinding.inflate(layoutInflater)
    }

    private val info = arrayListOf(
        "1.针对加载webView中的资源时加快加载的速度优化（主要是针对图片）",
        "2.WebView硬件加速导致页面渲染闪烁",
        "3.可以提前显示加载进度条",
        "4.WebView密码明文存储漏洞优化",
        "5.自定义加载异常error的状态页面，比如下面这些方法中可能会出现error",
        "6.WebView加载证书错误",
        "7.WebView音频播放销毁后还有声音",
        "8.如何设置白名单操作",
        "9.Android后台无法释放js导致发热耗电",
        "10.WebView加载网页不显示图片"
    )
    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        vb?.apply {
            val webSettings = wv.settings
            //设置加载进来的页面自适应手机屏幕
            webSettings.useWideViewPort = true
            webSettings.loadWithOverviewMode = true
            webSettings.useWideViewPort = true //设定支持viewport
            webSettings.loadWithOverviewMode = true
            webSettings.builtInZoomControls = true
            webSettings.displayZoomControls = false
            webSettings.javaScriptEnabled = true
            webSettings.setSupportZoom(true) //设定支持缩放
            //10
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            webSettings.blockNetworkImage = false
            webSettings.setPluginState(WebSettings.PluginState.ON)
            //在JS中调用本地java方法
    //            wv.addJavascriptInterface(
    //            )
            wv.webChromeClient = object : WebChromeClient() {
                override fun onProgressChanged(
                    view: WebView,
                    newProgress: Int
                ) {
                    super.onProgressChanged(view, newProgress)
                    Log.e("统计", "onProgressChanged: $newProgress")
                    if (newProgress == 100) {
                        endLoading()
                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    //5.
                    if (title?.contains("404")!!) {
                        topBar.setTitle("无法找到页面")
                    } else {
                        topBar.setTitle("$title")
                    }

                }
            }
            wv.webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)

                }

                //6.
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    super.onReceivedSslError(view, handler, error)
                    if (error != null && handler != null) {
                        val url = error.url
                        handler.proceed()
                    }
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    //5.
                    if (errorCode == 404) {
                        //用javascript隐藏系统定义的404页面信息
                        val data = "Page NO FOUND！";
                        view?.loadUrl("javascript:document.body.innerHTML=\"$data\"");
                    }
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    //1.
                    if (!view?.settings?.loadsImagesAutomatically!!) {
                        view.settings?.loadsImagesAutomatically = true;
                    }
                }
            }
            //4.
            webSettings.savePassword = false
            //1.
            webSettings.loadsImagesAutomatically =
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            //2.关闭硬件加速
            wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            //3.提前加载loading框
            startLoading()
            wv.loadUrl("https://www.baidu.com")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        //7.
        vb?.apply {
            val parent = wv.parent as ViewGroup
            parent.removeView(wv)
            wv.removeAllViews()
            wv.destroy()
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
        //9
        vb?.wv?.settings?.javaScriptEnabled = true
    }

    override fun onStop() {
        super.onStop()
        vb?.wv?.settings?.javaScriptEnabled = false
    }

}