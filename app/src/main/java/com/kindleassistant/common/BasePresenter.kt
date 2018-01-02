package com.kindleassistant.common

interface BasePresenter<T> {

    fun attachView(view: T)

    fun detachView()
}
