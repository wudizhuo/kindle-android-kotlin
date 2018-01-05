package com.kindleassistant

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.kindleassistant.di.AppComponent
import com.kindleassistant.di.AppModule
import com.kindleassistant.di.DaggerAppComponent
import io.fabric.sdk.android.Fabric



class App : Application() {
    companion object {
        lateinit var appContext: Context
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this.applicationContext
        Fabric.with(this, Crashlytics.Builder().core(CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build())

        //TODO 学习这个代码是怎么使用的
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }
}