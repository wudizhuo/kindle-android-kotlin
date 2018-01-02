package com.kindleassistant.sender

import android.os.Bundle
import com.kindleassistant.App
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), SenderContract.View {
    @Inject
    lateinit var presenter: SenderContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        setContentView(R.layout.activity_main)
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
