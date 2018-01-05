package com.kindleassistant.sender

import com.kindleassistant.common.BasePresenter
import io.reactivex.Observable


class SenderContract {
    interface View {
        fun previewIntent(): Observable<String>
        fun showContent(content: String)
        fun setProgressIndicator(visible: Int)
        fun showError(message: String)
    }

    abstract class Presenter : BasePresenter<View>()
}
