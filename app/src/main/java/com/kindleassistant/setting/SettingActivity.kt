package com.kindleassistant.setting

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.kindleassistant.AppPreferences
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val email = AppPreferences.toEmail
        if (!TextUtils.isEmpty(email)) {
            val split = email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (split.size == 2) {
                et_user_email.setText(split[0])
                val stringArray = resources.getStringArray(
                        R.array.sp_emails)
                val asList = Arrays.asList(*stringArray)
                val position = asList.indexOf("@" + split[1])
                sp_emails!!.setSelection(position, true)
            }
        }

        val userFromEmail = AppPreferences.fromEmail
        if (!TextUtils.isEmpty(userFromEmail)) {
            et_from_email.setText(userFromEmail)
        }

        bt_save_useremail.setOnClickListener {
            val userEmail = et_user_email.text.toString() + sp_emails!!.selectedItem.toString()
            val userFromEmail = et_from_email.text.toString()

            if (TextUtils.isEmpty(userEmail) || TextUtils.isEmpty(userFromEmail)) {
                Toast.makeText(applicationContext, "请设置完整",
                        Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AppPreferences.setEmail(userEmail)
            AppPreferences.setFromEmail(userFromEmail)
            Toast.makeText(applicationContext, "设置成功",
                    Toast.LENGTH_SHORT).show()
        }
        webview.loadUrl("file:///android_asset/setting-guide.html")
    }
}
