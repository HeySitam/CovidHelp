package com.sitamadex11.covidhelp.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
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
import com.google.android.material.button.MaterialButton
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
    lateinit var btnRetry: MaterialButton
    lateinit var btnExit: MaterialButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val isConnected = checkConnectivity(requireContext())
        if (!isConnected) {
            val customLayout = layoutInflater
                .inflate(
                    R.layout.network_check_dialog, null
                )
            msgInit(customLayout)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(customLayout)
            builder.setCancelable(false)
            val dialog = builder.create()
            btnExit.setOnClickListener{
                requireActivity().finish()
            }
            btnRetry.setOnClickListener{
                if(checkConnectivity(requireContext())){
                    //Do some thing
                    dialog.hide()
                    init(view)
                    click()
                }else{
                    Toast.makeText(requireContext(),"Sorry!! No Internet connection found",Toast.LENGTH_SHORT).show()
                }
            }
            dialog.show()
        } else {
            //Do some thing
            init(view)
            click()
        }
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
            R.id.cvVaccineBook -> {
                val url = "https://selfregistration.cowin.gov.in/"
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.data = Uri.parse(url)
                context?.startActivity(intent) ?: Toast.makeText(requireContext(),"Sorry Can't proceed",Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun msgInit(v: View?) {
        btnExit = v!!.findViewById(R.id.btnExit)
        btnRetry = v.findViewById(R.id.btnRetry)
    }


    fun checkConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected != null) {
            return activeNetwork.isConnected
        } else {
            return false
        }
    }
}