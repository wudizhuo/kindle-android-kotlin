package com.kindleassistant

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.kindleassistant.di.AppComponent
import com.kindleassistant.di.AppModule
import com.kindleassistant.di.DaggerAppComponent
import io.fabric.sdk.android.Fabric

class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        appComponent.inject(this)
    }
}