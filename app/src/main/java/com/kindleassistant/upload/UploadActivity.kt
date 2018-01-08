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
import com.jakewharton.rxbinding2.view.clicks
import com.kindleassistant.App
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import com.kindleassistant.setting.SettingActivity
import com.kindleassistant.util.ToastUtil
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar
import com.nononsenseapps.filepicker.FilePickerActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_uploads.*
import java.io.File
import javax.inject.Inject

private const val PERMISSIONS_REQUEST = 101
private const val FILE_SELECT_CODE = 0

class UploadActivity : BaseActivity(), UploadContract.View {
    @Inject
    lateinit var presenter: UploadContract.Presenter

    private var uploadFile = ""

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        setContentView(R.layout.activity_uploads)
        presenter.attachView(this)
        btn_select.setOnClickListener {
            checkAndShow()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
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

    override fun uploadFileIntent(): Observable<String> {
        return btn_upload.clicks()
                .map { uploadFile }
                .filter({ it ->
                    if (it.isEmpty()) {
                        Snackbar.make(btn_upload, R.string.snack_pick_file, Snackbar.LENGTH_LONG).show()
                    }
                    return@filter it.isNotEmpty()
                })
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
            file_description.text = this.uploadFile + "   大小为：" + String.format("%.5f", length / (1024 * 1024)) + "M"
        }
    }

    override fun setProgressIndicator(visibility: Int) {
        progressBar.visibility = visibility
    }

    override fun showError(message: String) {
        Snackbar.make(btn_upload, message, Snackbar.LENGTH_LONG).show()
    }

    override fun goToSetting() {
        startActivity(Intent(this, SettingActivity::class.java))
    }

    override fun showSuccess() {
        Snackbar.make(btn_upload, R.string.show_success, Snackbar.LENGTH_LONG).show()
    }

    override fun showProgress(percentage: Int) {
        (progressBar as CircleProgressBar).progress = percentage
    }
}
