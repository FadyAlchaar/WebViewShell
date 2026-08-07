package com.fady.webviewshell.manager

import android.content.Context

class AppPreferences(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "webview_shell",
            Context.MODE_PRIVATE
        )

    fun saveServerUrl(url: String) {
        prefs.edit().putString("server_url", url).apply()
    }

    fun getServerUrl(): String {
        return prefs.getString(
            "server_url",
            ""
        ) ?: ""
    }

    fun isConfigured(): Boolean {
        return getServerUrl().isNotEmpty()
    }

    fun savePin(pin: String) {
        prefs.edit().putString("admin_pin", pin).apply()
    }

    fun getPin(): String {
        return prefs.getString(
            "admin_pin",
            "1234"
        ) ?: "1234"
    }
}