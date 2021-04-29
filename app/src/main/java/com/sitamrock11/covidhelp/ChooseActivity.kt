package com.sitamrock11.covidhelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        cvNeedHelp.setOnClickListener {
            val intent= Intent(this,CitySelectionActivity::class.java)
            startActivity(intent)
        }
    }
}