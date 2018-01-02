package com.kindleassistant.sender

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import com.jakewharton.rxbinding2.view.clicks
import com.kindleassistant.App
import com.kindleassistant.R
import com.kindleassistant.common.BaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), SenderContract.View {
    @Inject
    lateinit var presenter: SenderContract.Presenter

    override fun previewIntent(): Observable<String> {
        return bt_preview.clicks()
                .map { et_user_url.text.toString() }
                .filter({ url ->
                    if (TextUtils.isEmpty(url)) {
                        //TODO show Toast
                        val toast = Toast.makeText(applicationContext,
                                "请填写文章链接", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    return@filter !TextUtils.isEmpty(url)
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        initView()
    }

    private fun initView() {
        bt_clear.setOnClickListener {
            et_user_url.setText("")
        }

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
        }
        setupNavigationDrawerContent(navigation_view as NavigationView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupNavigationDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(
                { menuItem ->
                    when (menuItem.itemId) {
                        R.id.item_navigation_drawer_help -> {

                        }
                        R.id.item_navigation_drawer_settings -> {

                        }
                        R.id.item_navigation_drawer_feedback -> {

                        }
                        R.id.item_navigation_drawer_upload -> {

                        }
                    }
                    true
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
