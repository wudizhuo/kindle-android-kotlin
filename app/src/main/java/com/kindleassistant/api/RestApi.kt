package com.kindleassistant.api


import com.kindleassistant.sender.model.entity.PreViewRequest
import com.kindleassistant.sender.model.entity.PreViewRsp
import com.kindleassistant.sender.model.entity.SendRequest
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RestApi {

    @POST("send")
    fun send(@Body send: SendRequest): Observable<Response<Unit>>

    @POST("preview")
    fun preview(@Body send: PreViewRequest): Observable<PreViewRsp>

    @Multipart
    @POST("upload")
    fun upload(@Part multipartBody: List<MultipartBody.Part>): Observable<Response<Unit>>
}
