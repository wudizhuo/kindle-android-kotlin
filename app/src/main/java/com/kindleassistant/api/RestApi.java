package com.kindleassistant.api;


import com.kindleassistant.sender.model.entity.PreViewRequest;
import com.kindleassistant.sender.model.entity.PreViewRsp;
import com.kindleassistant.sender.model.entity.SendRequest;

import java.util.List;

import io.reactivex.Observable;
import kotlin.Unit;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {

    @POST("send")
    Observable<Unit> send(@Body SendRequest send);

    @POST("preview")
    Observable<PreViewRsp> preview(@Body PreViewRequest send);

    @Multipart
    @POST("upload")
    Call<Void> upload(@Part List<MultipartBody.Part> multipartBody);
}
