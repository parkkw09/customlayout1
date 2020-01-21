package app.peterkwp.customlayout1.ui.kakaopay

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.TextView
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

        webView.webViewClient = object: WebViewClient() {

//            override fun shouldInterceptRequest(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): WebResourceResponse? {
//                request?.requestHeaders?.put("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJrYWthb0lkIjoiMTI1ODA0MTM1NSIsImRldmljZUtleSI6ImE1N2RjZmViYjcwYjQ1NjQiLCJjcmVhdGVEYXRlIjoiMjAyMC0wMS0yMFQwMjoyNzo0OC40NDdaIiwiaWF0IjoxNTc5NDg3MjY4LCJleHAiOjE1ODAwOTIwNjh9.FGNzyGQYo_71YJsK6-ES8LJ52OGdKG85I7fdmSpFJUA")
//                return super.shouldInterceptRequest(view, request)
//            }

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
        }

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


}