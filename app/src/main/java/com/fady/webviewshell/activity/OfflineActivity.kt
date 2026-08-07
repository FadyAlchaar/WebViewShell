package com.fady.webviewshell.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fady.webviewshell.R
import com.fady.webviewshell.manager.AppPreferences

class OfflineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_offline)

        val prefs = AppPreferences(this)

        val txtCurrentUrl = findViewById<TextView>(R.id.txtCurrentUrl)
        val btnRetry = findViewById<Button>(R.id.btnRetry)
        val btnSettings = findViewById<Button>(R.id.btnSettings)

        txtCurrentUrl.text = prefs.getServerUrl()

        btnRetry.setOnClickListener {

            startActivity(
                Intent(this, SplashActivity::class.java)
            )

            finish()
        }

        btnSettings.setOnClickListener {

            startActivity(
                Intent(this, SettingsActivity::class.java)
            )

            finish()
        }
    }
}