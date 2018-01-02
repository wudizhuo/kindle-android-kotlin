package com.kindleassistant.common

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T> {
    var loadDisposables: CompositeDisposable = CompositeDisposable()

    open fun attachView(view: T) {
        loadDisposables.clear()
    }

    open fun detachView() {
        loadDisposables.dispose()
    }
}

