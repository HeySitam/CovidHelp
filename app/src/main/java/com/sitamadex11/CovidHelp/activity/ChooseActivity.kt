package com.sitamadex11.CovidHelp.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.fragments.AboutUsFragment
import com.sitamadex11.CovidHelp.fragments.HomeFragment
import com.sitamadex11.CovidHelp.fragments.PrivacyPolicyFragment
import com.sitamadex11.CovidHelp.fragments.ViewProfileFragment
import com.sitamadex11.CovidHelp.worker.CovidTrackerWorker
import kotlinx.android.synthetic.main.activity_choose.*
import java.util.concurrent.TimeUnit


class ChooseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navDrawer: NavigationView
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var txtUserName: TextView
    lateinit var fragmentManager: FragmentManager
    lateinit var btnSend: MaterialButton
    lateinit var btnCancel: MaterialButton
    lateinit var etSendEmail: TextInputEditText
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
        fragmentManager = supportFragmentManager
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
            R.id.addVol -> {
                val intent = Intent(this, VolunteerActivity::class.java)
                startActivity(intent)
            }
            R.id.aboutUs -> {
                fragmentTransaction(AboutUsFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            R.id.contactUs -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                val customLayout = layoutInflater
                    .inflate(
                        R.layout.dialog_email, null
                    )
                msgInit(customLayout)
                val builder = AlertDialog.Builder(this)
                builder.setView(customLayout)
                val dialog = builder.create()
                btnCancel.setOnClickListener {
                    dialog.hide()
                }
                dialog.show()
                btnSend.setOnClickListener {
                    if (etSendEmail.text.isNullOrEmpty()) {
                        etSendEmail.error = "Please enter some text message"
                    } else {
                        val email = Intent(Intent.ACTION_SENDTO)
                        val to = "sitamadex11@gmail.com"
                        val subject = "Covid-Help FeedBack"
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = Uri.parse("mailto:$to") // only email apps should handle this
                        intent.putExtra(Intent.EXTRA_EMAIL, to)
                        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                        intent.putExtra(Intent.EXTRA_TEXT, etSendEmail.text.toString())
                        if (intent.resolveActivity(packageManager) != null) {
                            startActivity(intent)
                        }
                        etSendEmail.error = null
                    }
                }
            }
            R.id.logout -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                firebaseAuth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.privacyPolicy -> {
                fragmentTransaction(PrivacyPolicyFragment())
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        return true
    }

    private fun msgInit(v: View?) {
        btnSend = v!!.findViewById(R.id.btnSendEmail)
        btnCancel = v.findViewById(R.id.btnCancelEmail)
        etSendEmail = v.findViewById(R.id.etSendEmail)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (fragmentManager.backStackEntryCount > 0) {
            fragmentTransaction(HomeFragment())
            fragmentManager.popBackStack()
        } else {
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