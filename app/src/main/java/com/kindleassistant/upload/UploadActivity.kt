package com.kindleassistant.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.kindleassistant.AppPreferences
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import com.kindleassistant.setting.SettingActivity
import com.kindleassistant.util.ToastUtil
import com.nononsenseapps.filepicker.FilePickerActivity
import kotlinx.android.synthetic.main.activity_uploads.*
import kotlinx.android.synthetic.main.view_main.*
import java.io.File

private const val PERMISSIONS_REQUEST = 101
private const val FILE_SELECT_CODE = 0

class UploadActivity : BaseActivity() {
    private var uploadFile = ""

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploads)

        if (AppPreferences.fromEmail.isEmpty() || AppPreferences.toEmail.isEmpty()) {
            startActivity(Intent(this, SettingActivity::class.java))
            return
        }

        btn_select.setOnClickListener {
            checkAndShow()
        }
    }

    private fun checkAndShow() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSIONS_REQUEST)
        } else {
            showFileChooser()
        }
    }

    private fun uploadFile() {
        if (uploadFile.isEmpty()) {
            Snackbar.make(containerView, R.string.snack_pick_file, Snackbar.LENGTH_LONG).show()
            return
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFileChooser()
                } else {
                    ToastUtil.show(getString(R.string.permission_denied))
                    finish()
                }
            }
        }
    }

    private fun showFileChooser() {
        val intent = Intent(this, FilePickerActivity::class.java)
        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)
        intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false)
        intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE)
        intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().path)
        startActivityForResult(intent, FILE_SELECT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_SELECT_CODE && resultCode == Activity.RESULT_OK && data != null) {
            uploadFile = data.data.path

            val file = File(uploadFile)
            val length = file.length().toFloat()
            val fileText = findViewById<View>(R.id.file) as TextView
            fileText.text = this.uploadFile + "   大小为：" + String.format("%.5f", length / (1024 * 1024)) + "M"
        }
    }
}
