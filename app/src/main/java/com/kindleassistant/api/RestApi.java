package com.kindleassistant.api;


import com.kindleassistant.sender.model.entity.PreViewRequest;
import com.kindleassistant.sender.model.entity.PreViewRsp;
import com.kindleassistant.sender.model.entity.SendUrl;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {

    @POST("send")
    Call<Void> send(@Body SendUrl send);

    @POST("preview")
    Observable<PreViewRsp> preview(@Body PreViewRequest send);

    @Multipart
    @POST("upload")
    Call<Void> upload(@Part List<MultipartBody.Part> multipartBody);
}
