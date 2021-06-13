package com.sitamadex11.covidhelp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.model.District
import com.sitamadex11.covidhelp.model.DistrictItems
import com.sitamadex11.covidhelp.model.State
import com.sitamadex11.covidhelp.util.Constants
import kotlinx.android.synthetic.main.glass_signup_page.*

class ViewProfileFragment : Fragment(), View.OnClickListener {
    lateinit var txtProfileName: TextView
    lateinit var txtProfileEmail: TextView
    lateinit var txtProfileAddress: TextView
    lateinit var txtProfileState: TextView
    lateinit var txtProfileDistrict: TextView
    lateinit var txtProfilePhone: TextView
    lateinit var txtIsVol: TextView
    lateinit var firestore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var etEditName: TextInputEditText
    lateinit var etEditEmail: TextInputEditText
    lateinit var etEditPhone: TextInputEditText
    lateinit var etEditAddress: TextInputEditText
    lateinit var etEditState: AutoCompleteTextView
    lateinit var etEditDistrict: AutoCompleteTextView
    lateinit var btnEditProfile: FloatingActionButton
    lateinit var btnEdit: MaterialButton
    lateinit var dialog: AlertDialog
    val state_list = java.util.ArrayList<String>()
    val district_list = java.util.ArrayList<DistrictItems>()
    val district_name_list = java.util.ArrayList<String>()
    lateinit var requestOueue: RequestQueue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_profile, container, false)
        init(view)
        firestore.collection("users").get().addOnSuccessListener { querySnapShots ->
            for (snapshot in querySnapShots) {
                val uid = snapshot.getString("uid")
                if (firebaseAuth.currentUser!!.uid == uid) {
                    val name = snapshot.getString("UserName") ?: ""
                    val email = snapshot.getString("Email") ?: ""
                    val address = snapshot.getString("address") ?: ""
                    val state = snapshot.getString("state") ?: ""
                    val district = snapshot.getString("district") ?: ""
                    val phone = snapshot.getString("phone") ?: ""
                    val isVol = snapshot.getString("isVol") ?: ""
                    txtProfileName.text = name
                    txtProfileEmail.text = email
                    txtProfileAddress.text = address
                    txtProfileState.text = state
                    txtProfileDistrict.text = district
                    txtProfilePhone.text = phone
                    if (isVol == "0") {
                        txtIsVol.visibility = View.GONE
                    } else {
                        txtIsVol.visibility = View.VISIBLE
                    }
                }
            }
        }
        btnEditProfile.setOnClickListener(this)
        return view
    }

    private fun init(v: View) {
        txtProfileName = v.findViewById(R.id.txtProfileName)
        txtProfileEmail = v.findViewById(R.id.txtProfileEmail)
        txtProfileAddress = v.findViewById(R.id.txtProfileAddress)
        txtProfileState = v.findViewById(R.id.txtProfileState)
        txtProfileDistrict = v.findViewById(R.id.txtProfileDistrict)
        txtProfilePhone = v.findViewById(R.id.txtProfilePhone)
        btnEditProfile = v.findViewById(R.id.btnEditProfile)
        txtIsVol = v.findViewById(R.id.txtISVol)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        requestOueue = Volley.newRequestQueue(requireContext())
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnEditProfile -> {
                val customLayout = layoutInflater
                    .inflate(
                        R.layout.edit_profile, null
                    )
                editInit(customLayout)
                val builder = AlertDialog.Builder(requireContext())
                builder.setView(customLayout)
                dialog = builder.create()
                dialog.show()
                btnEdit.setOnClickListener(this)
                stateJsonParse()
            }
            R.id.btnEdit->{
                if(etEditName.text!!.isNotEmpty()
                    && etEditEmail.text!!.isNotEmpty()
                    && etEditPhone.text!!.isNotEmpty()
                    && etEditAddress.text!!.isNotEmpty()
                    && etEditState.text!!.isNotEmpty()
                    && etEditDistrict.text!!.isNotEmpty()) {
                    firestore.collection("users").get().addOnSuccessListener { querySnapShots ->
                        for (snapshot in querySnapShots) {
                            val uid = snapshot.getString("uid")
                            if (firebaseAuth.currentUser!!.uid == uid) {
                              val docRef= firestore.collection("users").document(snapshot.id)
                                docRef.update(mapOf(
                                    "Email" to etEditEmail.text.toString(),
                                    "UserName" to etEditName.text.toString(),
                                    "phone" to etEditPhone.text.toString(),
                                    "address" to etEditAddress.text.toString(),
                                    "state" to etEditState.text.toString(),
                                    "district" to etEditDistrict.text.toString()
                                )).addOnSuccessListener {
                                    Toast.makeText(requireContext(),
                                        "🤩 Your Profile updated successfully",
                                        Toast.LENGTH_SHORT).show()
                                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.frameBody,ViewProfileFragment()).commit()
                                    dialog.hide()
                                }.addOnFailureListener {
                                    Toast.makeText(requireContext(),
                                        "Sorry!! Something went wrong.",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
                if(etEditName.text.isNullOrEmpty()){
                    etEditName.error = "Your name can't be empty."
                }
                if(etEditEmail.text.isNullOrEmpty()){
                    etEditEmail.error = "Your email can't be empty."
                }
                if(etEditPhone.text.isNullOrEmpty()){
                    etEditPhone.error = "Your phone number can't be empty."
                }
                if(etEditAddress.text.isNullOrEmpty()){
                    etEditAddress.error = "Your Address can't be empty."
                }
                if(etEditState.text.isNullOrEmpty()){
                    etEditState.error = "Your State can't be empty."
                }
                if(etEditDistrict.text.isNullOrEmpty()){
                    etEditDistrict.error = "Your district can't be empty."
                }
            }
        }
    }

    private fun editInit(v: View?) {
        etEditName = v!!.findViewById(R.id.etEditName)
        etEditEmail = v.findViewById(R.id.etEditEmail)
        etEditPhone = v.findViewById(R.id.etEditPhone)
        etEditAddress = v.findViewById(R.id.etEditAddress)
        etEditState = v.findViewById(R.id.etEditState)
        etEditDistrict = v.findViewById(R.id.etEditDistrict)
        btnEdit = v.findViewById(R.id.btnEdit)
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
            etEditState.setAdapter(stateAdapter)
            etEditState.setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(requireContext(), state_list[position], Toast.LENGTH_SHORT).show()
                etEditDistrict.isEnabled = true
                district_list.clear()
                district_name_list.clear()
                districtJsonParse(position)
                etEditState.error = null
            }
            Log.d("chk_state", str)
        }, {
            Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT).show()
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
                ArrayAdapter(requireContext(), R.layout.dropdown_item, district_name_list)
            etEditDistrict.apply {
                setAdapter(stateAdapter)
            }
            Log.d("chk_state", str)
            etEditDistrict.setOnItemClickListener { parent, view, position, id ->
                etEditDistrict.error = null
            }
        }, {
            Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(districtString)
    }
}