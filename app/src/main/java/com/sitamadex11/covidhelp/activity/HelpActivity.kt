package com.sitamadex11.covidhelp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.fragments.CityWiseFragment
import com.sitamadex11.covidhelp.fragments.StateWiseFragment
import kotlinx.android.synthetic.main.help_activity.*

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_activity)
        bottom_navigation.setItemSelected(R.id.stateWise, true)
        fragmentTransaction(StateWiseFragment())
        bottom_navigation.setOnItemSelectedListener {
            lateinit var fragment: Fragment
            when (it) {
                R.id.stateWise -> {
                    fragment = StateWiseFragment()
                }
                R.id.cityWise -> {
                    fragment = CityWiseFragment()
                }
            }
            fragmentTransaction(fragment)
        }
    }

    fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment).commit()
    }
}