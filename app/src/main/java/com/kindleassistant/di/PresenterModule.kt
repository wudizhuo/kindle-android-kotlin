package com.kindleassistant.di

import com.kindleassistant.sender.SenderContract
import com.kindleassistant.sender.SenderPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    internal fun provideSenderPresenter(presenter: SenderPresenter): SenderContract.Presenter {
        return presenter
    }
}
