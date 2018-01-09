package com.kindleassistant.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kindleassistant.api.RestApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {
    @Provides
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    internal fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(TrackingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

        return builder.build()
    }


    @Provides
    @Singleton
    internal fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Provides
    @Singleton
    internal fun provideRestApi(@ForApplication context: Context,
                                httpClient: OkHttpClient,
                                gsonConverterFactory: GsonConverterFactory): RestApi {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.kindlezhushou.com/")
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(httpClient)
                .build()

        return retrofit.create(RestApi::class.java)
    }
}

internal class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
        val delegate = retrofit?.nextResponseBodyConverter<ResponseBody>(this, type!!, annotations)
        return Converter<ResponseBody, Any> { body -> if (body.contentLength() == 0L) null else delegate!!.convert(body) }
    }
}

//TODO add Tracking
internal class TrackingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val start = System.currentTimeMillis()
        val response = chain.proceed(request)
        val requestMillis = System.currentTimeMillis() - start

//        if (response.code() in 200..299) {
//            tracker.requestSuccess(server.serverName, request.url().encodedPath(), requestMillis)
//        } else {
//            tracker.requestError(
//                    serverName = server.serverName,
//                    host = request.url().host(),
//                    path = request.url().encodedPath(),
//                    status = response.code(),
//                    masheryError = response.header("X-Mashery-Error-Code"),
//                    requestMillis = requestMillis
//            )
//        }

        return response
    }
}