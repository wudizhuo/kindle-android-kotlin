package com.kindleassistant.di


import com.kindleassistant.App
import com.kindleassistant.sender.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (PresenterModule::class), (NetworkModule::class)])
interface AppComponent {
    fun inject(app: App)

    fun inject(activity: MainActivity)
}