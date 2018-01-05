package com.kindleassistant.util

import android.view.Gravity
import android.widget.Toast

import com.kindleassistant.App

object ToastUtil {
    fun show(text: CharSequence?) {
        if (text == null)
            return
        Toast.makeText(App.appContext, text, Toast.LENGTH_SHORT).show()
    }

    fun show(resId: Int) {
        Toast.makeText(App.appContext, resId, Toast.LENGTH_SHORT).show()
    }

    fun showInCenter(text: CharSequence?) {
        if (text == null)
            return
        val toast = Toast
                .makeText(App.appContext, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun showLongTimeInCenter(text: CharSequence?) {
        if (text == null)
            return
        val toast = Toast.makeText(App.appContext, text, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}
