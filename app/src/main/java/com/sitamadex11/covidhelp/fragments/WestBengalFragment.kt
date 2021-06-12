package com.sitamadex11.covidhelp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.adapter.SomeannoyingDialogAdapter
import com.sitamadex11.covidhelp.model.SomeannoynigDialogItems
import com.sitamadex11.covidhelp.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup


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
    lateinit var btnGetCylinder: MaterialButton
    lateinit var btnChatBot: MaterialButton
    lateinit var txtAvailBed: TextView
    lateinit var txtAvailCylinder: TextView
    lateinit var txtAskHelp: TextView
    lateinit var url: String
    lateinit var intent: Intent
    private val listName = arrayListOf(
        "Oxygen",
        "Ambulance",
        "Test Resources",
        "Medicines",
        "Helpdesk",
        "Plasma/Blood",
        "Doctor",
        "Home Services",
        "Meal Services",
        "Others"
    )
    private val itemList = ArrayList<SomeannoynigDialogItems>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_west_bengal, container, false)
        itemListAdder(R.drawable.oxygen_tank, "Oxygen", "https://covid.someannoying.com/oxy.php")
        itemListAdder(R.drawable.ambulance, "Ambulance", "https://covid.someannoying.com/amb.php")
        itemListAdder(
            R.drawable.app_logo,
            "Test Resources",
            "https://covid.someannoying.com/tests.php"
        )
        itemListAdder(R.drawable.app_logo, "Medicines", "https://covid.someannoying.com/med.php")
        itemListAdder(R.drawable.help_desk, "Helpdesk", "https://covid.someannoying.com/help.php")
        itemListAdder(
            R.drawable.app_logo,
            "Plasma/Blood",
            "https://covid.someannoying.com/plasma.php"
        )
        itemListAdder(R.drawable.app_logo, "Doctor", "https://covid.someannoying.com/doc.php")
        itemListAdder(R.drawable.home, "Home Services", "https://covid.someannoying.com/homes.php")
        itemListAdder(R.drawable.app_logo, "Meal Services", "https://covid.someannoying.com/food/")
        itemListAdder(R.drawable.app_logo, "Others", "https://covid.someannoying.com/others.php")

        btnEService = view!!.findViewById(R.id.btnEServce)
        btnResources = view!!.findViewById(R.id.btnResources)
        btnGetCylinder = view!!.findViewById(R.id.btnGetCylinder)
        btnChatBot = view!!.findViewById(R.id.btnChatBot)
        txtAvailBed = view.findViewById(R.id.txtAvailBed)
        txtAvailCylinder = view.findViewById(R.id.txtAvailCylinder)
        txtAskHelp = view.findViewById(R.id.txtAskHelp)
        fetchBedCylinderCount()
        btnEService.setOnClickListener(this)
        btnResources.setOnClickListener(this)
        btnGetCylinder.setOnClickListener(this)
        btnChatBot.setOnClickListener(this)
        txtAskHelp.setOnClickListener(this)
        return view
    }

    private fun fetchBedCylinderCount() {
        val someUrl = Constants.someUrl
        val wbUrl = Constants.wbUrl
        CoroutineScope(Dispatchers.IO).launch {
            val docSome = Jsoup.connect(someUrl).get()
            val docWb = Jsoup.connect(wbUrl).get()
            val cylinderCnt = docSome.select("#content [class='badge badge-pill badge-success']")
            val bedCnt = docWb.select("#counter2")
            withContext(Dispatchers.Main) {
                txtAvailBed.text = bedCnt.text()
                txtAvailCylinder.text = cylinderCnt.text().replace("Cylinders Available:", "")
            }
        }
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
                url =
                    "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Hospital_Bed_Availability.aspx"
                intentBrowse()
            }
            R.id.cvHome -> {
                url =
                    "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Safe_Home_Bed_Availability.aspx"
                intentBrowse()
            }
            R.id.cvHelpDesk -> {
                url =
                    "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_District_Contact_Details.aspx?Telephone_Number_Flag=A"
                intentBrowse()
            }
            R.id.cvCenter -> {
                url =
                    "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_District_Contact_Details.aspx?Telephone_Number_Flag=C"
                intentBrowse()
            }
            R.id.cvAmbulance -> {
                url = "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_MIS_Ambulance.aspx"
                intentBrowse()
            }
            R.id.cvHearse -> {
                url = "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_MIS_Hearse_Van.aspx"
                intentBrowse()
            }
            R.id.cvNodal -> {
                url =
                    "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Cremation_Nodal_Officer.aspx"
                intentBrowse()
            }
            R.id.cvBurial -> {
                url =
                    "https://excise.wb.gov.in/CHMS/Public/Page/CHMS_Public_Burial_Cremation_Information.aspx"
                intentBrowse()
            }
            R.id.btnResources -> {
                val customLayout = layoutInflater
                    .inflate(
                        R.layout.custom_dialog_layout_someannoying, null
                    )
                val someannoyingDialogAdapter = SomeannoyingDialogAdapter(requireContext())
                rvSomeannoying = customLayout.findViewById(R.id.rvSomeannoying)
                rvSomeannoying.layoutManager = GridLayoutManager(requireContext(), 2)
                rvSomeannoying.adapter = someannoyingDialogAdapter
                someannoyingDialogAdapter.updateList(itemList)
                val builder = AlertDialog.Builder(requireContext())
                builder.setView(customLayout)
                val dialog = builder.create()
                dialog.show()
            }
            R.id.btnGetCylinder -> {
                url = "https://covid.someannoying.com/project_oxygen/"
                intentBrowse()
            }
            R.id.btnChatBot -> {
                openWhatsapp("+917596056446")
            }
            R.id.txtAskHelp ->{
                url = "https://covid.someannoying.com/ask.html"
                intentBrowse()
            }
        }
    }

    private fun intentBrowse() {
        intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
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

    private fun itemListAdder(img: Int, name: String, url: String) {
        itemList.add(SomeannoynigDialogItems(img, name, url))
    }

    private fun openWhatsapp(num: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.`package` = "com.whatsapp"
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$num&text=Help Me")
        if (requireActivity().packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Please install whatsapp", Toast.LENGTH_SHORT).show()
        }
    }
}