package app.peterkwp.customlayout1.ui.kakaopay

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import app.peterkwp.customlayout1.App
import app.peterkwp.customlayout1.R
import com.google.gson.Gson
import com.google.gson.JsonArray
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.net.URISyntaxException
import javax.inject.Inject


class KakaoPayFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: KakaoPayViewModelFactory
    private lateinit var kakaoPayViewModel: KakaoPayViewModel

    val disposable: CompositeDisposable = CompositeDisposable()

    private fun transactionTest(): Disposable {
        val availableCards = JsonArray()
        availableCards.add("BC")
        availableCards.add("HANA")
        val jsonCards = Gson().toJson(availableCards)
        Log.d(App.TAG, "jsonCards=[ $jsonCards ]")
        return kakaoPayViewModel.transactionReady(
            cid = "TC0ONETIME",
            partner_order_id = "partner_order_id",
            partner_user_id = "partner_user_id",
            item_name = "초코파이",
            quantity =  "1",
            total_amount = "2200",
            tax_free_amount = "200",
            vat_amount = "0",
            approval_url = "https://developers.kakao.com/success",
            cancel_url = "https://developers.kakao.com/fail",
            fail_url = "https://developers.kakao.com/cancel",
            available_cards = jsonCards
        )
    }

    private fun transactionApproveTest(tid: String, token: String): Disposable {
        return kakaoPayViewModel.transactionApprove(
            cid = "TC0ONETIME",
            tid = tid,
            partner_order_id = "partner_order_id",
            partner_user_id = "partner_user_id",
            pg_token = token
        )
    }

    private fun transactionTest2(): Disposable {
        return kakaoPayViewModel.transactionReady2(
            payment = "CARD",
            mid = "INIpayTest",
            goods = "축구공",
            oid = "testoid",
            amt = "1000",
            uname = "홍길동",
            mname = "이니시스 쇼핑몰",
            email = "smart@inicis.com",
            next_url = "https://mobile.inicis.com/smart/testmall/next_url_test.php"
        )
    }

    private fun transactionApproveTest2(tid: String, token: String): Disposable {
        return kakaoPayViewModel.transactionApprove(
            cid = "TC0ONETIME",
            tid = tid,
            partner_order_id = "partner_order_id",
            partner_user_id = "partner_user_id",
            pg_token = token
        )
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI(view: View) {
        val textView: TextView = view.findViewById(R.id.text_title)
        val kakaopay: Button = view.findViewById(R.id.btn_kakao)
        val inicis: Button = view.findViewById(R.id.btn_inicis)
        val container: FrameLayout = view.findViewById(R.id.webview_container)
        val webView: WebView = view.findViewById(R.id.webview)

        kakaoPayViewModel.text.observe(this, Observer {
            textView.text = it
            if (it.contains("transaction complete")) {
                webView.loadUrl("about:blank")
            }
        })

        kakaoPayViewModel.url.observe(this, Observer {
            webView.loadUrl(it)
        })

        kakaoPayViewModel.responseBody.observe(this, Observer {
            webView.loadData(it, "text/html", "euc-kr")
        })

        webView.isVerticalScrollBarEnabled = false
        webView.settings.apply {
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
            setAcceptThirdPartyCookies(webView, true)
        }

        webView.webChromeClient = object : WebChromeClient() {

            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {
                Log.d(App.TAG, "onCreateWindow()")
                context?.run {
                    val newWebView = WebView(this)
                    newWebView.settings.apply {
                        javaScriptEnabled = true
                    }
                    container.addView(newWebView)

                    resultMsg?.run {
                        (obj as WebView.WebViewTransport).webView = newWebView
                        sendToTarget()
                    }

                    newWebView.webChromeClient = object : WebChromeClient() {

                        override fun onCreateWindow(
                            view: WebView?,
                            isDialog: Boolean,
                            isUserGesture: Boolean,
                            resultMsg: Message?
                        ): Boolean {
                            Log.d(App.TAG, "NewWebView onCreateWindow()")
                            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                        }

                        override fun onCloseWindow(window: WebView?) {
                            Log.i(App.TAG, "NewWebView onCloseWindow()")
                            container.removeView(newWebView)
                            super.onCloseWindow(window)
                        }
                    }
                    newWebView.webViewClient = object: WebViewClient() {

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            Log.d(App.TAG, "NewWebView onPageStarted() [$url]")
                            super.onPageStarted(view, url, favicon)
                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            Log.d(App.TAG, "NewWebView onPageFinished() [$url]")
                            super.onPageFinished(view, url)
                        }

                        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                            Log.d(App.TAG, "NewWebView shouldOverrideUrlLoading()[$url]")
                            return true
                        }

                        override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                            Log.d(App.TAG, "NewWebView shouldOverrideKeyEvent = [${event?.action}]")
                            return super.shouldOverrideKeyEvent(view, event)
                        }

                        override fun onRenderProcessGone(view: WebView, detail: RenderProcessGoneDetail): Boolean {
                            if (!detail.didCrash()) {
                                // Renderer was killed because the system ran out of memory.
                                // The app can recover gracefully by creating a new WebView instance
                                // in the foreground.
                                Log.e(App.TAG, ("NewWebView System killed the WebView rendering process " +
                                        "to reclaim memory. Recreating..."))

                                /**
                                 * Do work something!
                                 */

                                // By this point, the instance variable "mWebView" is guaranteed
                                // to be null, so it's safe to reinitialize it.

                                return true // The app continues executing.
                            }

                            // Renderer crashed because of an internal error, such as a memory access violation.
                            Log.e(App.TAG, "NewWebView The WebView rendering process crashed!")

                            // In this example, the app itself crashes after detecting that the
                            // renderer crashed. If you choose to handle the crash more gracefully
                            // and allow your app to continue executing, you should 1) destroy the
                            // current WebView instance, 2) specify logic for how the app can
                            // continue executing, and 3) return "true" instead.
                            /**
                             * Do work something!
                             */
                            return false
                        }
                    }
                    return true
                }

                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            }

            override fun onCloseWindow(window: WebView?) {
                Log.d(App.TAG, "onCloseWindow()")
                super.onCloseWindow(window)
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d(App.TAG, "onJsAlert() url:{$url}, message:${message}}")
                return super.onJsAlert(view, url, message, result)
            }

            override fun onJsConfirm(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Log.d(App.TAG, "onJsConfirm() url:{$url}, message:${message}}")
                return super.onJsConfirm(view, url, message, result)
            }
        }

        webView.webViewClient = object: WebViewClient() {

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
                when {
                    url.isEmpty() -> {
                        return false
                    }
                    url.contains("/success") -> {
                        val uri = Uri.parse(url)
                        Log.d(App.TAG, "scheme = [${uri.scheme}]")
                        Log.d(App.TAG, "authority = [${uri.authority}]")
                        Log.d(App.TAG, "path = [${uri.path}]")
                        Log.d(App.TAG, "query = [${uri.query}]")
                        uri.queryParameterNames.forEach { name ->
                            if ("pg_token" == name) {
                                // get name
                                Log.d(App.TAG, "pg_token exist")
                            }
                        }
                        uri.getQueryParameter("pg_token")?.run {
                            // get name
                            Log.d(App.TAG, "pg_token = [$this]")
                            kakaoPayViewModel.tid?.let { tid ->
                                transactionApproveTest(tid, this).apply {
                                    disposable.add(this)
                                }
                            }
                        }
                    }
                    url.startsWith("intent://") -> {
                        try {
                            activity?.run {
                                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                intent.`package`?.let { name ->
                                    if (packageManager.getLaunchIntentForPackage(name) != null) {
                                        startActivity(intent)
                                    } else {
                                        val marketIntent = Intent(Intent.ACTION_VIEW)
                                        marketIntent.data =
                                            Uri.parse("market://details?id=" + intent.getPackage())
                                        startActivity(marketIntent)
                                    }
                                } ?: return false
                                return true
                            }
                            return false
                        } catch (e: Exception) {
                            Log.e(App.TAG, "exception[${e.printStackTrace()}]")
                        }
                    }
                    url.startsWith("intent:hdcardappcardansimclick://") -> {
                        try {
                            activity?.run {
                                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                intent.`package`?.let { name ->
                                    if (packageManager.getLaunchIntentForPackage(name) != null) {
                                        startActivity(intent)
                                    } else {
                                        val marketIntent = Intent(Intent.ACTION_VIEW)
                                        marketIntent.data =
                                            Uri.parse("market://details?id=" + intent.getPackage())
                                        startActivity(marketIntent)
                                    }
                                } ?: return false
                                return true
                            }
                            return false
                        } catch (e: Exception) {
                            Log.e(App.TAG, "exception[${e.printStackTrace()}]")
                        }
                    }
                    url.startsWith("market://") -> {
                        try {
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            intent?.let { startActivity(it) }
                            return true
                        } catch (e: URISyntaxException) {
                            Log.e(App.TAG, "exception[${e.printStackTrace()}]")
                        }
                    }
                    else -> {
                        webView.loadUrl(url)
                    }
                }
                return true
            }

            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                Log.d(App.TAG, "shouldOverrideKeyEvent = [${event?.action}]")
                return super.shouldOverrideKeyEvent(view, event)
            }

            override fun onRenderProcessGone(view: WebView, detail: RenderProcessGoneDetail): Boolean {
                if (!detail.didCrash()) {
                    // Renderer was killed because the system ran out of memory.
                    // The app can recover gracefully by creating a new WebView instance
                    // in the foreground.
                    Log.e(App.TAG, ("System killed the WebView rendering process " +
                            "to reclaim memory. Recreating..."))

                    // do work

                    // By this point, the instance variable "mWebView" is guaranteed
                    // to be null, so it's safe to reinitialize it.

                    return true // The app continues executing.
                }

                // Renderer crashed because of an internal error, such as a memory access violation.
                Log.e(App.TAG, "The WebView rendering process crashed!")

                // In this example, the app itself crashes after detecting that the
                // renderer crashed. If you choose to handle the crash more gracefully
                // and allow your app to continue executing, you should 1) destroy the
                // current WebView instance, 2) specify logic for how the app can
                // continue executing, and 3) return "true" instead.
                return false
            }
        }

        webView.addJavascriptInterface(MyWebInterface(), "custom")

        kakaopay.setOnClickListener {
            transactionTest().apply {
                disposable.add(this)
            }
        }
        inicis.setOnClickListener {
            transactionTest2().apply {
                disposable.add(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(App.TAG, "onCreate()")
    }

    override fun onDestroy() {
        Log.d(App.TAG, "onDestroy()")
        disposable.clear()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        kakaoPayViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(KakaoPayViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_kakaopay, container, false)
        initUI(root)
        return root
    }

    inner class MyWebInterface {
        @JavascriptInterface
        fun onReceiveCustomWebViewStatus(status: String, message: String) {
            val value = "[${App.TAG}][$status][$message]"
            Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
        }
    }
}