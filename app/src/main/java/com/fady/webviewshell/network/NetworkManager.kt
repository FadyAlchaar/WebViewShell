package com.fady.webviewshell.network

import com.fady.webviewshell.manager.ShellSession
import com.fady.webviewshell.model.AppConfigResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkManager {

    fun loadConfiguration(baseUrl: String): Boolean {

        return try {

            val cleanBaseUrl = baseUrl.trim().removeSuffix("/")

            val url = URL("$cleanBaseUrl/api/app-config.php")

            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.doInput = true

            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                connection.disconnect()
                return false
            }

            val json = BufferedReader(
                InputStreamReader(connection.inputStream)
            ).use {
                it.readText()
            }

            connection.disconnect()

            val config = Gson().fromJson(
                json,
                AppConfigResponse::class.java
            )

            if (!config.success)
                return false

            ShellSession.baseUrl = cleanBaseUrl
            ShellSession.config = config
            ShellSession.appUrl =
                cleanBaseUrl + config.application.start_page

            true

        } catch (e: Exception) {

            false

        }

    }

}