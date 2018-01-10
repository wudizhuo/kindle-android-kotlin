package com.kindleassistant.preview

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : BaseActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        val content = intent.getStringExtra("content")

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressBar.progress = newProgress
                super.onProgressChanged(view, newProgress)
            }
        }
        webView.loadData(content, "text/html; charset=UTF-8", "utf-8")
    }
}
