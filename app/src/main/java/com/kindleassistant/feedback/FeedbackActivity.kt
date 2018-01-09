package com.kindleassistant.feedback

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kindleassistant.AppPreferences
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedbackActivity : BaseActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        val nickname = "Kindle用户"
        val headimgurl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3ITqUBw6uHLZaMR-zOyiggjAJWZ_lpb4T2moi9MutqpwuWJSQ"
        val openid = (AppPreferences.fromEmail + AppPreferences.toEmail).hashCode()
        val url = "https://support.qq.com/product/22012"

        val postData = "nickname=$nickname&avatar=$headimgurl&openid=$openid" as String

        webView.webViewClient = webViewClient
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressBar.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }
        }
        webView.postUrl(url, postData.toByteArray())
    }

    private var webViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            super.shouldOverrideUrlLoading(view, url)

            if (url == null) {
                return false
            }
            try {
                if (url.startsWith("weixin://")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view.context.startActivity(intent)
                    return true
                }
            } catch (e: Exception) {
                return false
            }

            view.loadUrl(url)
            return true
        }
    }
}
