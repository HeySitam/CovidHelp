package com.sitamadex11.covidhelp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sitamadex11.covidhelp.R
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        cvNeedHelp.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }
        cvDonate.setOnClickListener {
            val intent = Intent(this, DonationListActivity::class.java)
            startActivity(intent)
        }
        cvVolunteer.setOnClickListener {
            val intent = Intent(this, VolunteerActivity::class.java)
            startActivity(intent)
        }
        cvCowin.setOnClickListener {
            val intent = Intent(this, VaccineStatusActivity::class.java)
            startActivity(intent)
        }
    }
}