package app.peterkwp.customlayout1.ui.parts

import android.graphics.Bitmap
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R


class ImageHolder(val view: View): RecyclerView.ViewHolder(view) {
    var ivItem: ImageView = view.findViewById(R.id.ivItem)
    var ivItemCount: TextView = view.findViewById(R.id.ivItemCount)
}

class WebHolder(val view: View): RecyclerView.ViewHolder(view) {
    var ivItem: WebView = view.findViewById(R.id.ivItem)
    var ivItemCount: TextView = view.findViewById(R.id.ivItemCount)

    init {
        ivItem.isVerticalScrollBarEnabled = false
        ivItem.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            loadsImagesAutomatically = true
            loadWithOverviewMode = true
            useWideViewPort = false
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            allowUniversalAccessFromFileURLs = true
            setSupportMultipleWindows(true)
        }

        val cookieManager = CookieManager.getInstance()
        cookieManager.apply {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(ivItem, true)
        }

        ivItem.webChromeClient = object : WebChromeClient() {

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.i(App.TAG, "onJsAlert()   = url : {$url} ,  message : ${message}}")
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsConfirm(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.i(App.TAG, "onJsConfirm()   = url : {$url} ,  message : ${message}}")
                return super.onJsConfirm(view, url, message, result)
            }
        }

        ivItem.webViewClient = object: WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.d(App.TAG, "onPageStarted() [$url]")
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(App.TAG, "onPageFinished() [$url]")
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                Log.d(App.TAG, "shouldOverrideUrlLoading()[$url]")
                webView.loadUrl(url)
                return true
            }

            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                Log.d(App.TAG, "shouldOverrideKeyEvent = [${event?.action}]")
                return super.shouldOverrideKeyEvent(view, event)
            }
        }
    }
}

class FooterHolder(view: View): RecyclerView.ViewHolder (view)