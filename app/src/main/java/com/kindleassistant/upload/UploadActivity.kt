package com.kindleassistant.upload

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.kindleassistant.AppPreferences
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import com.kindleassistant.setting.SettingActivity
import com.kindleassistant.util.ToastUtil
import com.nononsenseapps.filepicker.FilePickerActivity
import kotlinx.android.synthetic.main.activity_uploads.*
import java.io.File

class UploadActivity : BaseActivity() {
    private var uploadFile = ""
    private val FILE_SELECT_CODE = 0

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploads)
        btn_select.setOnClickListener {
            showFileChooser()
        }
    }

    private fun updateClick() {
        if (TextUtils.isEmpty(AppPreferences.toEmail) || TextUtils.isEmpty(AppPreferences.fromEmail)) {
            ToastUtil.showInCenter("请先设置发送邮箱或者信任邮箱")
            Handler().postDelayed({
                val intent = Intent(this@UploadActivity,
                        SettingActivity::class.java)
                startActivity(intent)
            }, 100)
            return
        }
        uploadFile()
    }

    private fun uploadFile() {

    }

    private fun showFileChooser() {
        val intent = Intent(this, FilePickerActivity::class.java)
        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)
        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false)
        intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE)
        intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().path)
        startActivityForResult(intent, FILE_SELECT_CODE)
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data.data
            uploadFile = uri!!.path

            val file = File(uploadFile)
            val length = file.length().toFloat()
            val fileText = findViewById<View>(R.id.file) as TextView
            fileText.text = this.uploadFile + "   大小为：" + String.format("%.5f", length / (1024 * 1024)) + "M"
        }
    }
}
