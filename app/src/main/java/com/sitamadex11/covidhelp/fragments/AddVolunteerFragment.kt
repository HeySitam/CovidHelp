package com.sitamadex11.covidhelp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.model.District
import com.sitamadex11.covidhelp.model.State
import com.sitamadex11.covidhelp.util.Constants


class AddVolunteerFragment : Fragment(), View.OnClickListener {
    lateinit var etVolName: TextInputEditText
    lateinit var etVolPh: TextInputEditText
    lateinit var etVolState: AutoCompleteTextView
    lateinit var etVolDistrict: AutoCompleteTextView
    lateinit var etVolOrg: TextInputEditText
    lateinit var etVolDesc: TextInputEditText
    lateinit var btnVolAddFB: MaterialButton
    lateinit var requestOueue: RequestQueue
    val state_list = ArrayList<String>()
    val district_list = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_volunteer, container, false)
        init(view!!)
        stateJsonParse()
        callBack()
        btnVolAddFB.setOnClickListener(this)
        return view
    }

    private fun stateJsonParse() {
        val url = Constants.STATE_URL
        val stateString = StringRequest(url, { str ->
            val state_class = Gson().fromJson(str, State::class.java)
            for (i in state_class.states.indices) {
                state_list.add(state_class.states[i].state_name)
            }
            val stateAdapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_item, state_list)
            etVolState.setAdapter(stateAdapter)
            etVolState.setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(context, state_list[position], Toast.LENGTH_SHORT).show()
                etVolDistrict.isEnabled = true
                district_list.clear()
                districtJsonParse(position)
                etVolState.error=null
            }

            Log.d("chk_state", str)
        }, {
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(stateString)

    }

    private fun districtJsonParse(position: Int) {
        val url = "${Constants.DISTRICT_URL}${position}"
        Log.d("chk_url", url)
        val districtString = StringRequest(url, { str ->
            val district_class = Gson().fromJson(str, District::class.java)
            for (i in district_class.districts!!.indices) {
                district_list.add(district_class.districts[i]!!.district_name!!)
            }
            val stateAdapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_item, district_list)
            etVolDistrict.apply {
                setAdapter(stateAdapter)
                setOnItemClickListener { _, _, _, _ ->
                    etVolDistrict.error=null
                }
            }
            Log.d("chk_state", str)
        }, {
            Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(districtString)
    }

    private fun init(view: View) {
        etVolName = view.findViewById(R.id.etVolName)!!
        etVolPh = view.findViewById(R.id.etVolPh)
        etVolState = view.findViewById(R.id.etVolState)
        etVolDistrict = view.findViewById(R.id.etVolDistrict)
        etVolOrg = view.findViewById(R.id.etVolOrg)
        etVolDesc = view.findViewById(R.id.etVolDesc)
        btnVolAddFB = view.findViewById(R.id.btnVolAddFB)
        requestOueue = Volley.newRequestQueue(requireContext())
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnVolAddFB -> {
             dataCheckAndAdd()
            }
        }
    }

    private fun dataCheckAndAdd() {
        var chk = false
        if(etVolName.text.isNullOrEmpty()) {
            etVolName.apply {
                error = "Volunteer's name can't be empty"
            }
            chk=true
        }
        if(etVolPh.text.isNullOrEmpty()){
            etVolPh.apply {
                error="Volunteer's phone no. can't be empty"
            }
        chk=true
    }
        if(etVolState.text.isNullOrEmpty()){
            etVolState.apply {
                error="Volunteer's state can't be empty"
            }
        chk=true
    }
        if(etVolDistrict.text.isNullOrEmpty()){
            etVolDistrict.apply{
                error="Volunteer's District can't be empty"
            }
        chk=true
    }
        if(!chk){
            dataAdd()
            val item=arrayOf(etVolName,etVolPh,etVolState,etVolDistrict,etVolOrg,etVolDesc)
            for(i in item.indices)
                item[i].setText("")
        }
    }

    private fun fragmentTransaction(fragment : Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .addToBackStack(fragment.javaClass.name)
            .replace(R.id.flVolunteer,fragment)
            .commit()
    }
    private fun callBack(){
        val callBack=object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                fragmentTransaction(ViewVolunteerFragment())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }
    private fun dataAdd(){
        val user: MutableMap<String, Any> = HashMap()
        user["name"] = etVolName.text.toString()
        user["phone"] = etVolPh.text.toString()
        user["state"] = etVolState.text.toString()
        user["district"] = etVolDistrict.text.toString()
        user["organization"] = etVolOrg.text.toString()
        user["description"] = etVolDesc.text.toString()

        FirebaseFirestore.getInstance().collection("volunteers")
            .add(user)
            .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                Toast.makeText(
                    context,
                    "${etVolName.text.toString()} is added as volunteer successfully !!",
                    Toast.LENGTH_SHORT
                ).show()
            })
            .addOnFailureListener(OnFailureListener { e ->
                Log.w("FireStoreError", "Error adding document", e)
                Toast.makeText(
                    context,
                    "Sorry!! Something is went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            })
    }
}