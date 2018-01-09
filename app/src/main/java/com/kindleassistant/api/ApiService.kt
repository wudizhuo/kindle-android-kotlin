package com.kindleassistant.api

import com.kindleassistant.sender.model.entity.PreViewRequest
import com.kindleassistant.sender.model.entity.PreViewRsp
import com.kindleassistant.sender.model.entity.SendRequest
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class ApiService @Inject constructor() {
    @Inject
    internal lateinit var api: RestApi

    fun preview(url: String): Observable<PreViewRsp> {
        return api.preview(PreViewRequest(url))
    }

    fun send(request: SendRequest): Observable<Response<Unit>> {
        return api.send(request)
    }

    fun upload(multipartBody: List<MultipartBody.Part>): Observable<Response<Unit>> {
        return api.upload(multipartBody)
    }
}
