package com.sitamrock11.covidhelp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.sitamrock11.covidhelp.R
import com.sitamrock11.covidhelp.activity.WebActivity


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
    lateinit var url: String
    lateinit var intent: Intent
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_west_bengal, container, false)
        btnEService = view!!.findViewById(R.id.btnEServce)
        btnEService.setOnClickListener(this)
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

                val builder = AlertDialog.Builder(context!!)
                builder.setView(customLayout)
                val dialog = builder.create()
                dialog.show()
            }
            R.id.cvBed -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Hospital_Bed_Availability.aspx"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
            R.id.cvHome -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Safe_Home_Bed_Availability.aspx"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
            R.id.cvHelpDesk -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_District_Contact_Details.aspx?Telephone_Number_Flag=A"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
            R.id.cvCenter -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_District_Contact_Details.aspx?Telephone_Number_Flag=C"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
            R.id.cvAmbulance -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_MIS_Ambulance.aspx"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
            R.id.cvHearse -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_MIS_Hearse_Van.aspx"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
            R.id.cvNodal -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Cremation_Nodal_Officer.aspx"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
            R.id.cvBurial -> {
                url="https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Burial_Cremation_Information.aspx"
                intent=Intent(requireContext(),WebActivity::class.java)
                intent.putExtra("urlLink",url)
                context!!.startActivity(intent)
            }
        }
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