package com.kindleassistant

import android.preference.PreferenceManager
import java.util.*

object AppPreferences {
    private var sPreferences = PreferenceManager.getDefaultSharedPreferences(App
            .appContext)

    private val HAS_SLIDINGGUIDE = "has_slidingguide"
    private val USER_TO_EMAIL = "user_to_email"
    private val USER_FROM_EMAIL = "user_from_email"
    private val OPENID = "OPENID"

    val hasSlidingGuide: Boolean
        get() = sPreferences.getBoolean(HAS_SLIDINGGUIDE, false)

    val toEmail: String
        get() = sPreferences.getString(USER_TO_EMAIL, "")
    val fromEmail: String
        get() = sPreferences.getString(USER_FROM_EMAIL, "")

    fun setHasSlidingGuide(value: Boolean): Boolean {
        return sPreferences.edit().putBoolean(HAS_SLIDINGGUIDE, value).commit()
    }

    fun setEmail(value: String): Boolean {
        return sPreferences.edit().putString(USER_TO_EMAIL, value).commit()
    }

    fun setFromEmail(value: String): Boolean {
        return sPreferences.edit().putString(USER_FROM_EMAIL, value).commit()
    }

    val openId: String
        get() {
            var openId = sPreferences.getString(OPENID, "")
            if (openId.isEmpty()) {
                openId = UUID.nameUUIDFromBytes((AppPreferences.fromEmail + AppPreferences.toEmail).toByteArray()).toString()
                setOpenId(openId)
            }
            return openId
        }

    private fun setOpenId(value: String): Boolean {
        return sPreferences.edit().putString(OPENID, value).commit()
    }
}
