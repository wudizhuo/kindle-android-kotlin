package com.kindleassistant.sender

import android.view.View.GONE
import com.kindleassistant.api.ApiService
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
            //TODO add dispose  loadDisposables +
            loadDisposables += api.preview(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            //TODO add end show dialog
                            { mView.showContent(it.content) },
                            {
                                it.printStackTrace()
                                //TODO add show dialog
                                mView.setProgressIndicator(GONE)
                                mView.showError(it.message!!)
                            },
                            { mView.setProgressIndicator(GONE) }
                    )


        }
    }
}