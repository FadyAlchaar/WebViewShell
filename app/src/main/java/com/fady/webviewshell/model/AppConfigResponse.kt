package com.fady.webviewshell.model

data class AppConfigResponse(

    val success: Boolean,

    val application: Application,

    val branding: Branding,

    val shell: Shell,

    val server: Server

)

data class Application(

    val name: String,

    val company: String,

    val version: String,

    val start_page: String

)

data class Branding(

    val logo: String,

    val animation: String,

    val primary_color: String,

    val accent_color: String

)

data class Shell(

    val minimum_version: String,

    val recommended_version: String,

    val latest_version: String,

    val apk: String,

    val force_update: Boolean

)

data class Server(

    val maintenance: Boolean,

    val message: String

)