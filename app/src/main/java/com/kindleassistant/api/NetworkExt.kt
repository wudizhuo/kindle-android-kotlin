package com.kindleassistant.api

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

fun Throwable.getMessage(): String {
    if (this is HttpException) {
        return Gson().fromJson(this.response().errorBody()?.string(), HttpError::class.java).message
    }
    return this.message.orEmpty()
}