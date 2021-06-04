package com.sitamadex11.covidhelp.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sitamadex11.covidhelp.R
import kotlinx.android.synthetic.main.activity_choose.*

class ChooseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navDrawer: NavigationView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var txtUserName: TextView
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
        txtUserName.text = firebaseAuth.currentUser!!.displayName
    }

    private fun init() {
        drawerLayout = findViewById(R.id.drawerLayout)
        navDrawer = findViewById(R.id.navDrawer)
        txtUserName =
            navDrawer.getHeaderView(0).findViewById(R.id.txtUserName)
imgHamburger.setOnClickListener {
    drawerLayout.openDrawer(GravityCompat.START)
}
       // drawerLayout.addDrawerListener(toggle)

        navDrawer.setNavigationItemSelectedListener(this)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.viewUserProfile -> {
                Toast.makeText(this, "Let's view user profile", Toast.LENGTH_SHORT).show()
            }
            R.id.logout->{
                drawerLayout.closeDrawer(GravityCompat.START)
                firebaseAuth.signOut()
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}