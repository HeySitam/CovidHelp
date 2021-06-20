package com.sitamadex11.covidhelp.activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.sitamadex11.covidhelp.R
import kotlinx.android.synthetic.main.activity_web.*


class WebActivity : AppCompatActivity() {
    lateinit var btnRetry: MaterialButton
    lateinit var btnExit: MaterialButton

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConnected = checkConnectivity(this)
        if (!isConnected) {
            val customLayout = layoutInflater
                .inflate(
                    R.layout.network_check_dialog, null
                )
            msgInit(customLayout)
            val builder = AlertDialog.Builder(this)
            builder.setView(customLayout)
            builder.setCancelable(false)
            val dialog = builder.create()
            btnExit.setOnClickListener {
                finish()
            }
            btnRetry.setOnClickListener {
                if (checkConnectivity(this)) {
                    //Do some thing
                    dialog.hide()
                    setContentView(R.layout.activity_web)
                    val url = intent.getStringExtra("urlLink")
                    println(url)
                    webView.webViewClient = WebViewClient()
                    webView.loadUrl(url!!)
                    val webSetting = webView.settings
                    webSetting.javaScriptEnabled = true
                    webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_INSET;
                } else {
                    Toast.makeText(this, "Sorry!! No Internet connection found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            dialog.show()
        } else {
            //Do some thing
            setContentView(R.layout.activity_web)
            val url = intent.getStringExtra("urlLink")
            println(url)
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url!!)
            val webSetting = webView.settings
            webSetting.javaScriptEnabled = true
            webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_INSET;
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else {
            super.onBackPressed()
        }
    }

    fun setDesktopMode(webView: WebView, enabled: Boolean) {
        var newUserAgent = webView.settings.userAgentString
        if (enabled) {
            try {
                val ua = webView.settings.userAgentString
                val androidOSString =
                    webView.settings.userAgentString.substring(ua.indexOf("("), ua.indexOf(")") + 1)
                newUserAgent =
                    webView.settings.userAgentString.replace(androidOSString, "(X11; Linux x86_64)")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            newUserAgent = null
        }
        webView.settings.userAgentString = newUserAgent
        webView.settings.useWideViewPort = enabled
        webView.settings.loadWithOverviewMode = enabled
        webView.reload()
    }

    private fun msgInit(v: View?) {
        btnExit = v!!.findViewById(R.id.btnExit)
        btnRetry = v.findViewById(R.id.btnRetry)
    }


    fun checkConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected != null) {
            return activeNetwork.isConnected
        } else {
            return false
        }
    }
}