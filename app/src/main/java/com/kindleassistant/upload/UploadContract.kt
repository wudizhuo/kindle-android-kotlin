package com.kindleassistant.upload

import com.kindleassistant.common.BasePresenter
import io.reactivex.Observable


class UploadContract {
    interface View {
        fun uploadFileIntent(): Observable<String>
        fun setProgressIndicator(visibility: Int)
        fun showError(message: String)
        fun goToSetting()
        fun showSuccess()
        fun showProgress(percentage: Int)
    }

    abstract class Presenter : BasePresenter<View>()
}
