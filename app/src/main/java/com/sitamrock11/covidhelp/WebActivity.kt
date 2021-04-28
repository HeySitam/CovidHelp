package com.sitamrock11.covidhelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url=intent.getStringExtra("urlLink")
        println(url)
        webView.webViewClient= WebViewClient()
        webView.loadUrl(url!!)
        val webSetting=webView.settings
        webSetting.javaScriptEnabled=true
    }
}