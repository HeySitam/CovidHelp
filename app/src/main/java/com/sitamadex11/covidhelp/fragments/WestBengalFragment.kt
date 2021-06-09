package com.sitamadex11.covidhelp.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.adapter.SomeannoyingDialogAdapter


class WestBengalFragment : Fragment(), View.OnClickListener {
    lateinit var btnEService: MaterialButton
    lateinit var cvBed: MaterialCardView
    lateinit var cvSafeHome: MaterialCardView
    lateinit var cvHelpDesk: MaterialCardView
    lateinit var cvCenter: MaterialCardView
    lateinit var cvAmbulance: MaterialCardView
    lateinit var cvHearse: MaterialCardView
    lateinit var cvNodal: MaterialCardView
    lateinit var cvBurial: MaterialCardView
    lateinit var rvSomeannoying: RecyclerView
    lateinit var btnResources: MaterialButton
    lateinit var url: String
    lateinit var intent: Intent
    val listName = arrayListOf("Oxygen", "Ambulance", "Test Resources", "Medicines","Helpdesk","Plasma/Blood", "Doctor", "Home Services", "Meal Services", "Others")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_west_bengal, container, false)
        btnEService = view!!.findViewById(R.id.btnEServce)
        btnResources = view!!.findViewById(R.id.btnResources)
        btnEService.setOnClickListener(this)
        btnResources.setOnClickListener(this)
        return view
    }

    private fun init(customLayout: View) {
        cvBed = customLayout.findViewById(R.id.cvBed)
        cvSafeHome = customLayout.findViewById(R.id.cvHome)
        cvHelpDesk = customLayout.findViewById(R.id.cvHelpDesk)
        cvCenter = customLayout.findViewById(R.id.cvCenter)
        cvAmbulance = customLayout.findViewById(R.id.cvAmbulance)
        cvHearse = customLayout.findViewById(R.id.cvHearse)
        cvNodal = customLayout.findViewById(R.id.cvNodal)
        cvBurial = customLayout.findViewById(R.id.cvBurial)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnEServce -> {
                val customLayout = layoutInflater
                    .inflate(
                        R.layout.custom_dialog_layout_wb, null
                    )
                init(customLayout)
                clickHandle()

                val builder = AlertDialog.Builder(requireContext())
                builder.setView(customLayout)
                val dialog = builder.create()
                dialog.show()
            }
            R.id.cvBed -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Hospital_Bed_Availability.aspx"
                intentBrowse()
            }
            R.id.cvHome -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Safe_Home_Bed_Availability.aspx"
                intentBrowse()
            }
            R.id.cvHelpDesk -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_District_Contact_Details.aspx?Telephone_Number_Flag=A"
                intentBrowse()
            }
            R.id.cvCenter -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_District_Contact_Details.aspx?Telephone_Number_Flag=C"
                intentBrowse()
            }
            R.id.cvAmbulance -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_MIS_Ambulance.aspx"
                intentBrowse()
            }
            R.id.cvHearse -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_MIS_Hearse_Van.aspx"
                intentBrowse()
            }
            R.id.cvNodal -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Cremation_Nodal_Officer.aspx"
                intentBrowse()
            }
            R.id.cvBurial -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Burial_Cremation_Information.aspx"
                intentBrowse()
            }
            R.id.btnResources -> {
                val customLayout = layoutInflater
                    .inflate(
                        R.layout.custom_dialog_layout_someannoying, null
                    )
                val someannoyingDialogAdapter =SomeannoyingDialogAdapter(requireContext())
                rvSomeannoying=customLayout.findViewById(R.id.rvSomeannoying)
                rvSomeannoying.layoutManager = GridLayoutManager(requireContext(),2)
                rvSomeannoying.adapter=someannoyingDialogAdapter
                someannoyingDialogAdapter.updateList(listName)
                val builder = AlertDialog.Builder(requireContext())
                builder.setView(customLayout)
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
private fun intentBrowse(){
    intent=Intent()
    intent.action=Intent.ACTION_VIEW
    intent.data= Uri.parse(url)
    requireContext().startActivity(intent)
}
    private fun clickHandle() {
        cvBed.setOnClickListener(this)
        cvSafeHome.setOnClickListener(this)
        cvHelpDesk.setOnClickListener(this)
        cvCenter.setOnClickListener(this)
        cvAmbulance.setOnClickListener(this)
        cvHearse.setOnClickListener(this)
        cvNodal.setOnClickListener(this)
        cvBurial.setOnClickListener(this)
    }
}