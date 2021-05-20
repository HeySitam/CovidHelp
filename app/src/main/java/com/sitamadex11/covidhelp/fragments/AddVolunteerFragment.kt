package com.sitamadex11.covidhelp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sitamadex11.covidhelp.R

class AddVolunteerFragment : Fragment(),View.OnClickListener {
    lateinit var etVolName:TextInputEditText
    lateinit var etVolPh:TextInputEditText
    lateinit var etVolState:AutoCompleteTextView
    lateinit var etVolDistrict:AutoCompleteTextView
    lateinit var etVolOrg:TextInputEditText
    lateinit var etVolDesc:TextInputEditText
    lateinit var btnVolAddFB:MaterialButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_volunteer, container, false)
        init(view)
        val state= arrayOf("kerala","ladakh","manipur","west bengal")
        val district= arrayOf("kolkata","Sitam","Rupu")
        val stateAdapter=ArrayAdapter(requireContext(),R.layout.dropdown_item,state)
        etVolState.setAdapter(stateAdapter)
        return view
    }
    private fun init(view:View?){
        etVolName=view!!.findViewById(R.id.etVolName)
        etVolPh=view.findViewById(R.id.etVolPh)
        etVolState=view.findViewById(R.id.etVolState)
        etVolDistrict=view.findViewById(R.id.etVolDistrict)
        etVolOrg=view.findViewById(R.id.etVolOrg)
        etVolDesc=view.findViewById(R.id.etVolDesc)
        btnVolAddFB=view.findViewById(R.id.btnVolAddFB)
    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }
    }
}