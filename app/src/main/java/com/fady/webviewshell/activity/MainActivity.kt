package com.fady.webviewshell.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.fady.webviewshell.R
import com.fady.webviewshell.manager.AppPreferences
import com.fady.webviewshell.manager.ShellSession
import com.fady.webviewshell.manager.WebViewManager
import android.view.View


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)

        findViewById<View>(R.id.adminHotspot)
            .setOnLongClickListener {

                showPinDialog()

                true
            }

        WebViewManager(
            this,
            webView,
            progressBar
        ).initialize(
            ShellSession.appUrl
        )

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {

                override fun handleOnBackPressed() {

                    if (webView.canGoBack())
                        webView.goBack()
                    else
                        finish()

                }

            }
        )

    }

    private fun showPinDialog() {

        val view =
            layoutInflater.inflate(
                R.layout.dialog_pin,
                null
            )

        val txtPin =
            view.findViewById<EditText>(
                R.id.txtPin
            )

        AlertDialog.Builder(this)
            .setTitle("Administrator Access")
            .setView(view)
            .setPositiveButton("OK") { _, _ ->

                val prefs = AppPreferences(this)

                if (txtPin.text.toString() == prefs.getPin()) {

                    startActivity(
                        Intent(
                            this,
                            SettingsActivity::class.java
                        )
                    )

                } else {

                    Toast.makeText(
                        this,
                        "Incorrect PIN",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
            .setNegativeButton("Cancel", null)
            .show()

    }

}