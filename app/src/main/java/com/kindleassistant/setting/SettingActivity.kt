package com.kindleassistant.setting

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import com.kindleassistant.AppPreferences
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val fromEmailPref = AppPreferences.fromEmail
        if (TextUtils.isEmpty(fromEmailPref)) {
            input_layout_from_email.isErrorEnabled = true
            input_layout_from_email.error = getString(R.string.from_email_hint)
        } else {
            et_from_email.setText(fromEmailPref)
        }

        val toEmailPref = AppPreferences.toEmail
        if (TextUtils.isEmpty(toEmailPref)) {
            input_layout_to_email.isErrorEnabled = true
            input_layout_to_email.error = getString(R.string.to_email_hint)
        } else {
            val split = toEmailPref.split(Regex("@")).dropLastWhile { it.isEmpty() }.toTypedArray()
            et_to_email.setText(split[0])
            val position = resources.getStringArray(
                    R.array.sp_emails).indexOf("@" + split[1])
            sp_emails.setSelection(position, true)
        }
        webview.loadUrl("file:///android_asset/setting-guide.html")

        bt_save.setOnClickListener {

            val fromEmail = et_from_email.text.toString()
            fromEmail.isNotEmpty().apply {
                input_layout_from_email.isErrorEnabled = false
                AppPreferences.setFromEmail(fromEmail)
            }
            et_to_email.text.isNotEmpty().apply {
                val toEmail = et_to_email.text.toString() + sp_emails.selectedItem.toString()
                input_layout_to_email.isErrorEnabled = false
                AppPreferences.setToEmail(toEmail)
            }

            Snackbar.make(bt_save, R.string.show_done, Snackbar.LENGTH_LONG).show()
        }
    }
}
