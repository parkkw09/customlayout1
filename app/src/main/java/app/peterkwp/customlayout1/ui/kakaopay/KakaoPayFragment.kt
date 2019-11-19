package app.peterkwp.customlayout1.ui.kakaopay

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
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
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class KakaoPayFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: KakaoPayViewModelFactory
    private lateinit var kakaoPayViewModel: KakaoPayViewModel

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI(view: View) {
        val textView: TextView = view.findViewById(R.id.text_title)
        val kakaopay: Button = view.findViewById(R.id.btn_kakao)
        val inicis: Button = view.findViewById(R.id.btn_inicis)
        val webView: WebView = view.findViewById(R.id.webview)

        kakaoPayViewModel.text.observe(this, Observer {
            textView.text = it
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
            setSupportMultipleWindows(true)
        }

        webView.webViewClient = object: WebViewClient() {

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                Log.d(App.TAG, "onReceivedSslError()")
                handler?.proceed()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.d(App.TAG, "onPageFinished()")
                super.onPageFinished(view, url)
            }

            override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
                Log.d(App.TAG, "shouldOverrideUrlLoading()[$url]")
                webView.loadUrl(url)
                return true
            }
        }

        kakaopay.setOnClickListener {
            webView.loadUrl("https://www.kakaopay.com/")
        }
        inicis.setOnClickListener {
            webView.loadUrl("https://www.inicis.com/")
        }
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