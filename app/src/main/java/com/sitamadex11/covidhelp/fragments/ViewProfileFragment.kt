package com.sitamadex11.covidhelp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sitamadex11.covidhelp.R

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
                val dialog = builder.create()
                dialog.show()
                btnEdit.setOnClickListener(this)
            }
            R.id.btnEdit->{
                Toast.makeText(requireContext(),"Let Edit Profile",Toast.LENGTH_SHORT).show()
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
}