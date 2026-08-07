package com.fady.webviewshell.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fady.webviewshell.R
import com.fady.webviewshell.manager.AppPreferences

class SettingsActivity : AppCompatActivity() {

    private lateinit var txtUrl: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        txtUrl = findViewById(R.id.txtUrl)
        btnSave = findViewById(R.id.btnSave)

        val prefs = AppPreferences(this)

        val savedUrl = prefs.getServerUrl()

        if (savedUrl.isBlank()) {
            txtUrl.setText("http://")
            txtUrl.setSelection(txtUrl.text.length)
        } else {
            txtUrl.setText(savedUrl)
            txtUrl.setSelection(txtUrl.text.length)
        }

        btnSave.setOnClickListener {

            var url = txtUrl.text.toString().trim()

            if (url.isEmpty() || url == "http://") {
                txtUrl.error = "Enter server URL"
                return@setOnClickListener
            }

            if (!url.startsWith("http://") &&
                !url.startsWith("https://")) {

                url = "http://$url"
            }

            prefs.saveServerUrl(url)

            Toast.makeText(
                this,
                "Settings Saved",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(this, SplashActivity::class.java)
            )

            finish()
        }
    }
}