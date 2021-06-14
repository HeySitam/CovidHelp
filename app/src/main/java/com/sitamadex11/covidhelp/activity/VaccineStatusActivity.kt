package com.sitamadex11.covidhelp.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.adapter.VaccineCenterListAdapter
import com.sitamadex11.covidhelp.model.CenterItem
import com.sitamadex11.covidhelp.model.District
import com.sitamadex11.covidhelp.model.DistrictItems
import com.sitamadex11.covidhelp.model.State
import com.sitamadex11.covidhelp.util.Constants
import com.sitamadex11.covidhelp.viewModel.StateViewModel
import com.sitamadex11.covidhelp.viewModel.StateViewModelFactory
import kotlinx.android.synthetic.main.activity_vaccine_status.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VaccineStatusActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    lateinit var myCalendar: Calendar
    private lateinit var etVacDate: TextInputEditText
    lateinit var requestOueue: RequestQueue
    lateinit var viewModelFactory: StateViewModelFactory
    lateinit var viewModel: StateViewModel
    lateinit var btnSearchCenter:MaterialButton
    val state_list = ArrayList<String>()
    val district_list = ArrayList<DistrictItems>()
    val district_name_list = ArrayList<String>()
    val adapter = VaccineCenterListAdapter(this)
    val centerList=ArrayList<CenterItem>()
    val centerSpecifiedList=ArrayList<CenterItem>()
    val filterItems = arrayOf("All","Available Only","Age 45+","Age 18+","Free","Paid","Covishield","Covaxin","Sputnik V")
    lateinit var rvVac: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccine_status)
        init()
        setClick()
        stateJsonParse()
        filterSetUp()
    }
    private fun notFoundShow(){
        if(centerSpecifiedList.isEmpty()){
            imgNoResult.visibility=View.VISIBLE
        }else{
            imgNoResult.visibility=View.GONE
        }
    }

    private fun filterSetUp() {
        val filterAdapter =
            ArrayAdapter(this, R.layout.dropdown_item, filterItems)
        etVacFilter.setAdapter(filterAdapter)
        etVacFilter.setOnItemClickListener { parent, view, position, id ->
            val filterItem = filterItems[position]
            filterCheck(filterItem)
        }
    }

    private fun filterCheck(filterItem: String) {
        if(filterItem=="All"){
            adapter.updateList(centerList)
            imgNoResult.visibility=View.GONE
        }else if(filterItem=="Available Only"){
            for(i in centerList.indices){
                if(centerList[i].sessions[0].dose1>0 || centerList[i].sessions[0].dose2>0){
                    centerSpecifiedList.add(centerList[i])
                }
            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }else if(filterItem=="Age 45+"){
            for(i in centerList.indices){
                if(centerList[i].sessions[0].minAgeLimit==45){
                    centerSpecifiedList.add(centerList[i])
                }

            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }else if(filterItem=="Age 18+"){
            for(i in centerList.indices){
                if(centerList[i].sessions[0].minAgeLimit==18){
                    centerSpecifiedList.add(centerList[i])
                }

            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }else if(filterItem=="Free"){
            for(i in centerList.indices){
                if(centerList[i].feeType=="Free"){
                    centerSpecifiedList.add(centerList[i])
                }

            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }else if(filterItem=="Paid"){
            for(i in centerList.indices){
                if(centerList[i].feeType=="Paid"){
                    centerSpecifiedList.add(centerList[i])
                }
            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }else if(filterItem=="Covishield"){
            for(i in centerList.indices){
                if(centerList[i].sessions[0].vaccine=="COVISHIELD"){
                    centerSpecifiedList.add(centerList[i])
                }
            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }else if(filterItem=="Covaxin"){
            for(i in centerList.indices){
                if(centerList[i].sessions[0].vaccine=="COVAXIN"){
                    centerSpecifiedList.add(centerList[i])
                }
            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }else if(filterItem=="Sputnik V"){
            for(i in centerList.indices){
                if(centerList[i].sessions[0].vaccine=="SPUTNIK V"){
                    centerSpecifiedList.add(centerList[i])
                }
            }
            adapter.updateList(centerSpecifiedList)
            notFoundShow()
            centerSpecifiedList.clear()
        }
    }

    private fun setClick() {
        etVacDate.setOnClickListener(this)
        btnSearchCenter.setOnClickListener(this)
    }

    private fun init() {
        etVacDate = findViewById(R.id.etVacDate)
        btnSearchCenter=findViewById(R.id.btnSearchCenter)
        rvVac = findViewById(R.id.rvVaccineCenter)
        rvVac.layoutManager = LinearLayoutManager(this)
        requestOueue = Volley.newRequestQueue(this)
        viewModelFactory = StateViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StateViewModel::class.java)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.etVacDate -> {
                etVacDate.error=null
                setDateListener()
            }
            R.id.btnSearchCenter->{
                if(!dataCheck()) {
                    val stringRequest =
                        viewModel.jsonParse(txtStateId.text.toString(), etVacDate.text.toString())
                    viewModel.myResponse.observe(this, androidx.lifecycle.Observer {
                        if(it.centers.isNotEmpty()) {
                            centerList.clear()
                            centerList.addAll(it.centers)
                          //  adapter.updateList(centerList)
                            val filterItem =etVacFilter.text.toString()
                            rvVac.adapter = adapter
                            filterCheck(filterItem)
                            tilVacFilter.isEnabled=true
                            imgNoResult.visibility=View.GONE
                        }else{
                            centerList.clear()
                            adapter.updateList(centerList)
                            imgNoResult.visibility=View.VISIBLE
                        }
                    })
                    requestOueue.add(stringRequest)
                }else{
                    if(etVacDate.text.isNullOrEmpty())
                    etVacDate.apply {
                        error="Select a valid date."
                    }
                    if(etVacState.text.isNullOrEmpty())
                    etVacState.apply {
                        error="State can't be empty"
                    }
                    if(etVacState.text.isNullOrEmpty())
                    etVacDistrict.apply{
                        error="District can't be empty"
                    }
                }
            }
        }
    }


    private fun setDateListener() {
        myCalendar = Calendar.getInstance()
        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDate()
        }
        val datePickerDialog = DatePickerDialog(
            this, dateSetListener, myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun updateDate() {
        // 24-05-2021
        val format = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(format)
        etVacDate.setText(sdf.format(myCalendar.time))
    }

    private fun stateJsonParse() {
        val url = Constants.STATE_URL
        val stateString = StringRequest(url, { str ->
            val state_class = Gson().fromJson(str, State::class.java)
            for (i in state_class.states.indices) {
                state_list.add(state_class.states[i].state_name)
            }
            val stateAdapter =
                ArrayAdapter(this, R.layout.dropdown_item, state_list)
            etVacState.setAdapter(stateAdapter)
            etVacState.setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(this, state_list[position], Toast.LENGTH_SHORT).show()
                etVacDistrict.isEnabled = true
                district_list.clear()
                district_name_list.clear()
                districtJsonParse(position)
                etVacState.error = null
            }

            Log.d("chk_state", str)
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(stateString)

    }

    private fun districtJsonParse(position: Int) {
        val url = "${Constants.DISTRICT_URL}${position}"
        Log.d("chk_url", url)
        val districtString = StringRequest(url, { str ->
            val district_class = Gson().fromJson(str, District::class.java)
            for (i in district_class.districts!!.indices) {
                district_list.add(district_class.districts[i]!!)
                district_name_list.add(district_class.districts[i]!!.district_name!!)
            }
            val stateAdapter =
                ArrayAdapter(this, R.layout.dropdown_item, district_name_list)
            etVacDistrict.apply {
                setAdapter(stateAdapter)
                setOnItemClickListener { _, _, position, _ ->
                    txtStateId.text = district_list[position].district_id.toString()
                    etVacDistrict.error = null
                }
            }
            Log.d("chk_state", str)
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(districtString)
    }
    private fun dataCheck():Boolean{
        var chk = false
        if(etVacDate.text.isNullOrEmpty()){
            chk=true
        }
        if(etVacState.text.isNullOrEmpty()){
            chk=true
        }
        if(etVacDistrict.text.isNullOrEmpty()){
            chk=true
        }
        return chk
    }
}