package com.sitamadex11.CovidHelp.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.sitamadex11.CovidHelp.R
import android.view.WindowManager





class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val colorCodeDark: Int = Color.parseColor("#F4B185")
        window.setStatusBarColor(colorCodeDark)

        Handler().postDelayed(Runnable {
            val i = Intent(
                this,
                LoginActivity::class.java
            )
            //Intent is used to switch from one activity to another.
            startActivity(i)
            //invoke the SecondActivity.
            finish()
            //the current activity will get finished.
        }, 2000)
    }
}