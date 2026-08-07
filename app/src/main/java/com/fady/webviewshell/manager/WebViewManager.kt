package com.fady.webviewshell.manager

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.fady.webviewshell.activity.OfflineActivity
import android.os.Handler
import android.os.Looper

class WebViewManager(
    private val activity: AppCompatActivity,
    private val webView: WebView,
    private val progressBar: ProgressBar
) {

    private val handler = Handler(Looper.getMainLooper())

    private val timeoutRunnable = Runnable {

        progressBar.visibility = View.GONE

        activity.startActivity(
            Intent(activity, OfflineActivity::class.java)
        )

        activity.finish()

    }
    @SuppressLint("SetJavaScriptEnabled")
    fun initialize(url: String) {

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.loadsImagesAutomatically = true

        webView.webChromeClient = WebChromeClient()

        webView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
            ) {

                progressBar.visibility = View.VISIBLE

                handler.removeCallbacks(timeoutRunnable)

                handler.postDelayed(
                    timeoutRunnable,
                    8000
                )

            }

            override fun onPageFinished(
                view: WebView?,
                url: String?
            ) {

                handler.removeCallbacks(timeoutRunnable)

                progressBar.visibility = View.GONE

            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {

                handler.removeCallbacks(timeoutRunnable)
                progressBar.visibility = View.GONE

                if (request != null && request.isForMainFrame) {

                    activity.startActivity(
                        Intent(
                            activity,
                            OfflineActivity::class.java
                        )
                    )

                    activity.finish()

                }

            }
        }

        webView.loadUrl(url)
    }
}