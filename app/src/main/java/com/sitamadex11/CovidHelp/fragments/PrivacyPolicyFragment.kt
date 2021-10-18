package com.sitamadex11.CovidHelp.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.util.Constants

/**
 * @author Joseph Olugbohunmi
 * @since Oct 03, 2021
 * https://github.com/mayorJAY
 */
class PrivacyPolicyFragment : Fragment() {

    private lateinit var activity: AppCompatActivity
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        loadWebFromUrl()
    }

    private fun init(view: View) {
        activity = getActivity() as AppCompatActivity
        webView = view.findViewById(R.id.webView)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.progressDrawable.setColorFilter(resources.getColor(R.color.purple_500), PorterDuff.Mode.SRC_IN)
        progressBar.setBackgroundColor(Color.parseColor("#1A000000"))
        setSystemBarColor(activity)
        setSystemBarLight(activity)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebFromUrl() {
        webView.settings.javaScriptEnabled = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.INVISIBLE
            }
        }
        webView.loadUrl(Constants.PRIVACY_POLICY_URL)
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                progressBar.progress = progress + 10
            }
        }
    }

    private fun setSystemBarColor(act: Activity) {
        val window = act.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.parseColor("#f2f2f2")
    }

    private fun setSystemBarLight(act: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val view = act.findViewById<View>(android.R.id.content)
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }
}