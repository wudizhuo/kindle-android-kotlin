package com.kindleassistant.upload

import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.kindleassistant.AppPreferences
import com.kindleassistant.api.ApiService
import com.kindleassistant.api.HttpError
import com.kindleassistant.api.ProgressRequestBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import java.io.File
import java.util.*
import javax.inject.Inject


class UploadPresenter @Inject constructor() : UploadContract.Presenter() {
    @Inject
    lateinit var api: ApiService
    private lateinit var mView: UploadContract.View

    override fun attachView(view: UploadContract.View) {
        super.attachView(view)
        mView = view


        loadDisposables += mView.uploadFileIntent()
                .filter({
                    if (AppPreferences.fromEmail.isEmpty() || AppPreferences.toEmail.isEmpty()) {
                        view.goToSetting()
                        return@filter false
                    } else {
                        return@filter true
                    }
                })
                .map {
                    val list = ArrayList<MultipartBody.Part>()
                    val progressRequestBody = ProgressRequestBody(File(it), ProgressRequestBody.UploadCallbacks { percentage -> view.showProgress(percentage) })
                    list.add(MultipartBody.Part.createFormData("file", File(it).name, progressRequestBody))
                    list.add(MultipartBody.Part.createFormData("from_email", AppPreferences.fromEmail))
                    list.add(MultipartBody.Part.createFormData("to_email", AppPreferences.toEmail))
                    return@map list
                }.subscribe {
            mView.setProgressIndicator(VISIBLE)
            loadDisposables += api.upload(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { mView.showSuccess() },
                            {
                                it.printStackTrace()
                                if (it is HttpException) {
                                    mView.showError(Gson().fromJson(it.response().errorBody()?.string(), HttpError::class.java).message.orEmpty())
                                } else {
                                    mView.showError(it.message!!)
                                }
                                mView.setProgressIndicator(GONE)

                            },
                            { mView.setProgressIndicator(GONE) }
                    )


        }
    }
}