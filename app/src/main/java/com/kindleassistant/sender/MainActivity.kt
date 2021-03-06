package com.kindleassistant.sender

import android.content.ClipDescription.MIMETYPE_TEXT_HTML
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.view.clicks
import com.kindleassistant.App
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import com.kindleassistant.feedback.FeedbackActivity
import com.kindleassistant.helper.HelperActivity
import com.kindleassistant.preview.PreviewActivity
import com.kindleassistant.setting.SettingActivity
import com.kindleassistant.upload.UploadActivity
import com.kindleassistant.util.ToastUtil
import com.roger.catloadinglibrary.CatLoadingView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), SenderContract.View {
    @Inject
    lateinit var presenter: SenderContract.Presenter
    private lateinit var loadingView: CatLoadingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        initView()

        checkShareUrl()
    }

    private fun getContentUrl(observable: Observable<Unit>): Observable<String> {
        return observable
                .map { et_content_url.text.toString() }
                .filter({ url ->
                    if (TextUtils.isEmpty(url)) {
                        input_layout_content.isErrorEnabled = true
                        input_layout_content.error = getString(R.string.please_input_content)
                    } else {
                        input_layout_content.isErrorEnabled = false
                    }
                    !TextUtils.isEmpty(url)
                })
    }

    override fun previewIntent() = getContentUrl(bt_preview.clicks())

    override fun sendIntent() = getContentUrl(bt_send.clicks())

    private fun checkShareUrl(): Boolean {
        if (Intent.ACTION_SEND == intent.action && intent.type != null) {
            if ("text/plain" == intent.type) {
                handleShareText(intent)
                return true
            } else if (intent.type.startsWith("image/")) {
                handleSendImage(intent)
                return true
            }
        }
        return false
    }

    //TODO 分享图片
    private fun handleSendImage(intent: Intent) {
//        val imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM) as Uri
//        if (imageUri != null) {
//            // Update UI to reflect image being shared
//        }
    }

    private fun handleShareText(intent: Intent) {
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        sharedText.isNotEmpty().apply {
            et_content_url.setText(sharedText)
        }
    }

    private fun initView() {
        loadingView = CatLoadingView()
        bt_clear.setOnClickListener {
            et_content_url.setText("")
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }
        setupNavigationDrawerContent(navigation_view as NavigationView)
    }

    override fun onResume() {
        super.onResume()
        getClipboardData()
    }

    private fun getClipboardData() {
        if (et_content_url.text != null) {
            return
        }
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (clipboard.hasPrimaryClip() && (clipboard.primaryClipDescription.hasMimeType(MIMETYPE_TEXT_PLAIN) || clipboard.primaryClipDescription.hasMimeType(MIMETYPE_TEXT_HTML))) {
            val pasteData = clipboard.primaryClip.getItemAt(0).text
            et_content_url.setText(pasteData)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(
                { menuItem ->
                    when (menuItem.itemId) {
                        R.id.item_navigation_drawer_help -> {
                            startActivity(Intent(this, HelperActivity::class.java))
                        }
                        R.id.item_navigation_drawer_settings -> {
                            startActivity(Intent(this, SettingActivity::class.java))
                        }
                        R.id.item_navigation_drawer_feedback -> {
                            startActivity(Intent(this, FeedbackActivity::class.java))
                            ToastUtil.show("欢迎到我的微博：无敌卓，反馈沟通。")
                        }
                        R.id.item_navigation_drawer_upload -> {
                            startActivity(Intent(this, UploadActivity::class.java))
                        }
                    }
                    true
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showContent(content: String) {
        val intent = Intent()
        intent.setClass(this, PreviewActivity::class.java)
        intent.putExtra("content", content)
        startActivity(intent)
    }

    override fun setProgressIndicator(visibility: Int) {
        if (visibility == View.VISIBLE) {
            loadingView.show(supportFragmentManager, "loadingView")
        } else {
            loadingView.dismiss()
        }
    }

    override fun showError(message: String) {
        Snackbar.make(containerView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun goToSetting() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    override fun showSuccess() {
        Snackbar.make(containerView, R.string.show_success, Snackbar.LENGTH_LONG).show()
    }
}
