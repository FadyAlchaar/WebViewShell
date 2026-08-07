package com.fady.webviewshell.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fady.webviewshell.R
import com.fady.webviewshell.config.AppConfig
import com.fady.webviewshell.manager.AppPreferences
import com.fady.webviewshell.network.NetworkManager
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val txtVersion = findViewById<TextView>(R.id.txtVersion)
        txtVersion.text = "Version ${AppConfig.APP_VERSION}"

        val prefs = AppPreferences(this)

        if (prefs.getPin().isEmpty()) {
            prefs.savePin("1234")
        }

        if (!prefs.isConfigured()) {

            Handler(Looper.getMainLooper()).postDelayed({

                startActivity(
                    Intent(this, SettingsActivity::class.java)
                )

                finish()

            }, 1600)

            return
        }

        thread {

            val success =
                NetworkManager().loadConfiguration(
                    prefs.getServerUrl()
                )

            runOnUiThread {

                if (success) {

                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )

                } else {

                    startActivity(
                        Intent(
                            this,
                            OfflineActivity::class.java
                        )
                    )

                }

                finish()

            }

        }

    }

}