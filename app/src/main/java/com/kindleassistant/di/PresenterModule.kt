package com.kindleassistant.di

import com.kindleassistant.sender.SenderContract
import com.kindleassistant.sender.SenderPresenter
import com.kindleassistant.upload.UploadContract
import com.kindleassistant.upload.UploadPresenter
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

    @Provides
    @Singleton
    internal fun provideUploadPresenter(presenter: UploadPresenter): UploadContract.Presenter {
        return presenter
    }
}
