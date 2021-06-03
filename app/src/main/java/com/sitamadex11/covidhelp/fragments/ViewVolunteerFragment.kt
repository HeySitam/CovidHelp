package com.sitamadex11.covidhelp.fragments

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.activity.ChooseActivity
import com.sitamadex11.covidhelp.adapter.VLAdapter
import com.sitamadex11.covidhelp.adapter.VolunteerListAdapter
import com.sitamadex11.covidhelp.model.District
import com.sitamadex11.covidhelp.model.DistrictItems
import com.sitamadex11.covidhelp.model.State
import com.sitamadex11.covidhelp.model.VolunteerDetailsModel
import com.sitamadex11.covidhelp.util.Constants
import kotlinx.android.synthetic.main.fragment_view_volunteer.*


class ViewVolunteerFragment : Fragment(), View.OnClickListener, VLAdapter {
    lateinit var btnAddVolunteer: FloatingActionButton
    lateinit var rvVol: RecyclerView
    lateinit var adapter: VolunteerListAdapter
    lateinit var requestOueue: RequestQueue
    val allVolunteers = ArrayList<VolunteerDetailsModel>()
    val state_list = java.util.ArrayList<String>()
    val district_list = java.util.ArrayList<DistrictItems>()
    val district_name_list = java.util.ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_volunteer, container, false)
        init(view)
        click()
        callBack()
        stateJsonParse()
        return view
    }

    private fun callBack() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun click() {
        btnAddVolunteer.setOnClickListener(this)
    }

    private fun init(view: View) {
        btnAddVolunteer = view.findViewById(R.id.btnAddVolunteer)
        rvVol = view.findViewById(R.id.rvVolunteer)
        rvVol.layoutManager = LinearLayoutManager(context)
        adapter = VolunteerListAdapter(requireContext(), this)
        requestOueue = Volley.newRequestQueue(requireContext())
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnAddVolunteer -> {
                fragmentTransition(AddVolunteerFragment())
            }

        }
    }

    private fun fragmentTransition(fragment: Fragment) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.flVolunteer, fragment)
            .commit()
    }

    private fun addItemToList() {
        allVolunteers.clear()
        FirebaseFirestore.getInstance().collection("volunteers").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                for (snapshot in queryDocumentSnapshots) {
                    val name = snapshot.getString("name")
                    val state = snapshot.getString("state")
                    val district = snapshot.getString("district")
                    val phoneS: String? = snapshot.getString("phone")
                    val org = snapshot.getString("organization")
                    // val phone=Integer.parseInt(phoneS!!)
                    if (etVolState.text.toString() == state && etVolDistrict.text.toString() == district) {
                        allVolunteers.add(VolunteerDetailsModel(name, phoneS, state, district, org))
                    }
                    // Log.d("ref_check", img.toString())
                }
                adapter.updateList(allVolunteers)
                if (allVolunteers.isNotEmpty())
                    txtVolInfo.visibility = View.GONE
                else {
                    txtVolInfo.visibility = View.VISIBLE
                    txtVolInfo.text = "Sorry!! No Volunteer Found in your area"
                }
                rvVol.adapter = adapter
            }.addOnFailureListener { e ->
                Log.d("EXPLOREFRAGMENT", "onFailure: " + e.message)
                txtVolInfo.text = "Can't Fetch Data"
                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkPermission(): Boolean {
        val callPermission = ContextCompat.checkSelfPermission(
            requireActivity().applicationContext,
            CALL_PHONE
        )
        return callPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(CALL_PHONE),
            200
        )
    }


    override fun onCallBtnClicked(phone: String) {
        if (!checkPermission()) {
            requestPermission()
        } else {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)
        }
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
                Toast.makeText(requireContext(), state_list[position], Toast.LENGTH_SHORT).show()
                etVolDistrict.isEnabled = true
                district_list.clear()
                district_name_list.clear()
                districtJsonParse(position)
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
            etVolDistrict.apply {
                setAdapter(stateAdapter)
                setOnItemClickListener { _, _, position, _ ->
                    txtVolInfo.visibility = View.VISIBLE
                    txtVolInfo.text = "Fetching data..."
                    addItemToList()
                }
            }
            Log.d("chk_state", str)
        }, {
            Toast.makeText(requireContext(), "something went wrong", Toast.LENGTH_SHORT).show()
        })
        requestOueue.add(districtString)
    }
}