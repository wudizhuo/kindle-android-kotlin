package com.kindleassistant.sender

import android.view.View.GONE
import android.view.View.VISIBLE
import com.kindleassistant.AppPreferences
import com.kindleassistant.api.ApiService
import com.kindleassistant.api.getMessage
import com.kindleassistant.sender.model.entity.SendRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SenderPresenter @Inject constructor() : SenderContract.Presenter() {
    @Inject
    lateinit var api: ApiService
    private lateinit var mView: SenderContract.View

    override fun attachView(view: SenderContract.View) {
        super.attachView(view)
        mView = view

        loadDisposables += mView.previewIntent().subscribe {
            mView.setProgressIndicator(VISIBLE)
            loadDisposables += api.preview(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { mView.showContent(it.content) },
                            {
                                it.printStackTrace()
                                mView.setProgressIndicator(GONE)
                                mView.showError(it.getMessage())
                            },
                            { mView.setProgressIndicator(GONE) }
                    )


        }

        loadDisposables += mView.sendIntent().map {
            SendRequest(it, AppPreferences.fromEmail, AppPreferences.toEmail)
        }.filter({
            if (it.from_email.isEmpty() || it.to_email.isEmpty()) {
                view.goToSetting()
                return@filter false
            } else {
                return@filter true
            }
        }).subscribe {
            mView.setProgressIndicator(VISIBLE)
            loadDisposables += api.send(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { mView.showSuccess() },
                            {
                                it.printStackTrace()
                                mView.setProgressIndicator(GONE)
                                mView.showError(it.getMessage())
                            },
                            { mView.setProgressIndicator(GONE) }
                    )


        }
    }
}