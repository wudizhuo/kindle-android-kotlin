package com.kindleassistant.api

import com.kindleassistant.api.RestApi
import com.kindleassistant.sender.model.entity.PreViewRequest
import com.kindleassistant.sender.model.entity.PreViewRsp
import io.reactivex.Observable
import javax.inject.Inject

class ApiService @Inject constructor() {
    @Inject
    internal lateinit var api: RestApi

    fun preview(url: String): Observable<PreViewRsp> {
        return api.preview(PreViewRequest(url))
    }
}
