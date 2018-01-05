package com.kindleassistant.helper

import android.os.Bundle
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_helper.*

class HelperActivity : BaseActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)
        webview.loadUrl("file:///android_asset/helper.html")
    }
}
