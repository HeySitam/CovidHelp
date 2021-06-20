package com.sitamadex11.covidhelp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.fragments.ViewVolunteerFragment

class VolunteerActivity : AppCompatActivity() {
    lateinit var json: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)
        val fragment = ViewVolunteerFragment()
        fragmentTransaction(fragment)
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flVolunteer, fragment).commit()
    }
}