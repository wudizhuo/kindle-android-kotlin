package com.kindleassistant.sender

import javax.inject.Inject

class SenderPresenter @Inject constructor() : SenderContract.Presenter {
    private var mView: SenderContract.View? = null

    override fun attachView(view: SenderContract.View) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }

    override fun loadData() {

    }
}