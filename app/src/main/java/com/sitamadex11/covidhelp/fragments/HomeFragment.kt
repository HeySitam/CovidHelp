package com.sitamadex11.covidhelp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.activity.*

class HomeFragment : Fragment(),
    View.OnClickListener {
    lateinit var navDrawer: NavigationView
    lateinit var cvNeedHelp: CardView
    lateinit var cvDonate: CardView
    lateinit var cvVolunteer: CardView
    lateinit var cvVaccine: CardView
    lateinit var cvCovidTracker: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        init(view)
        click()
        return view
    }

    private fun init(v: View) {
        cvNeedHelp = v.findViewById(R.id.cvNeedHelp)
        cvDonate = v.findViewById(R.id.cvDonate)
        cvVolunteer = v.findViewById(R.id.cvVolunteer)
        cvVaccine = v.findViewById(R.id.cvVaccine)
        cvCovidTracker = v.findViewById(R.id.cvCovidTracker)
    }

    private fun click() {
        cvNeedHelp.setOnClickListener(this)
        cvDonate.setOnClickListener(this)
        cvVolunteer.setOnClickListener(this)
        cvVaccine.setOnClickListener(this)
        cvCovidTracker.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.cvNeedHelp -> {
                val intent = Intent(requireContext(), HelpActivity::class.java)
                startActivity(intent)
            }
            R.id.cvDonate -> {
                val intent = Intent(requireContext(), DonationListActivity::class.java)
                startActivity(intent)
            }
            R.id.cvVolunteer -> {
                val intent = Intent(requireContext(), VolunteerActivity::class.java)
                startActivity(intent)
            }
            R.id.cvVaccine -> {
                val intent = Intent(requireContext(), VaccineStatusActivity::class.java)
                startActivity(intent)
            }
            R.id.cvCovidTracker -> {
                val intent = Intent(requireContext(), CovidTrackerActivity::class.java)
                startActivity(intent)
            }
        }
    }
}