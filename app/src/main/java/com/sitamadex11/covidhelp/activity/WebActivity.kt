package com.sitamadex11.covidhelp.activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.sitamadex11.covidhelp.R
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
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_INSET;
    }

    override fun onBackPressed() {
        if(webView.canGoBack()) webView.goBack()
        else {
//            val intent= Intent(this,CitySelectionActivity::class.java)
//            intent.putExtra("EXIT", true)
//            startActivity(intent)
            super.onBackPressed()
//            finish()
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
}