package com.sitamadex11.covidhelp.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sitamadex11.covidhelp.R
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        init()
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
        cvVaccine.setOnClickListener {
            val intent = Intent(this, VaccineStatusActivity::class.java)
            startActivity(intent)
        }
        cvCovidTracker.setOnClickListener {
            val intent = Intent(this, CovidTrackerActivity::class.java)
            startActivity(intent)
        }
        hamburger.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun init() {
        drawerLayout = findViewById(R.id.drawerLayout)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.viewUserProfile -> {
                Toast.makeText(this, "Let's view user profile", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}