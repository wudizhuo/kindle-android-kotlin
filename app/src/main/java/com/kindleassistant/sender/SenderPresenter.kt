package com.kindleassistant.sender

import com.kindleassistant.api.ApiService
import io.reactivex.rxkotlin.plusAssign
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
            api.preview(it).subscribe {
                //TODO add observer
            }
        }
    }
}