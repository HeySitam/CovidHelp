package com.sitamadex11.covidhelp.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.fragments.HomeFragment
import com.sitamadex11.covidhelp.fragments.ViewProfileFragment
import com.sitamadex11.covidhelp.worker.CovidTrackerWorker
import kotlinx.android.synthetic.main.activity_choose.*
import java.util.concurrent.TimeUnit

class ChooseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navDrawer: NavigationView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var txtUserName: TextView
    lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        init()
        initWorker()
        fragmentTransaction(HomeFragment())
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

        navDrawer.setNavigationItemSelectedListener(this)
        firebaseAuth = FirebaseAuth.getInstance()
        fragmentManager=supportFragmentManager
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeItem -> {
                fragmentTransaction(HomeFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.viewUserProfile -> {
                fragmentTransaction(ViewProfileFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.logout -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                firebaseAuth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if(fragmentManager.backStackEntryCount>0){
            fragmentTransaction(HomeFragment())
            fragmentManager.popBackStack()
        }else {
            super.onBackPressed()
        }
    }

    private fun fragmentTransaction(fragment: Fragment) {
        if (fragmentManager.backStackEntryCount == 0) {
            fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frameBody, fragment).commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.frameBody, fragment).commit()
        }
    }
    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<CovidTrackerWorker>(6, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueue(
            notificationWorkRequest
        )
    }
}