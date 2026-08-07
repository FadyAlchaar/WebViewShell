package com.fady.webviewshell.manager

import java.net.HttpURLConnection
import java.net.URL

object ConnectionChecker {

    fun isServerReachable(urlString: String): Boolean {

        return try {

            val connection = URL(urlString).openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.instanceFollowRedirects = true

            connection.connect()

            val code = connection.responseCode

            connection.disconnect()

            code in 200..399

        } catch (e: Exception) {

            false

        }

    }

}