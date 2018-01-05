package com.kindleassistant.preview

import android.os.Bundle
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_preview.*

//TODO 右上角放发送按钮
class PreviewActivity : BaseActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        var content = intent.extras.getString("content")
        webView.loadData(content, "text/html; charset=UTF-8", "utf-8")
    }
}
