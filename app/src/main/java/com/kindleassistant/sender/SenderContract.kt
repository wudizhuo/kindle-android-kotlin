package com.kindleassistant.sender

import com.kindleassistant.common.BasePresenter
import io.reactivex.Observable


class SenderContract {
    interface View{
        fun previewIntent(): Observable<String>
    }

    abstract class Presenter: BasePresenter<View>()
}
