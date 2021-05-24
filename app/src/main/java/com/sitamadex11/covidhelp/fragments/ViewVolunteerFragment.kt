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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.adapter.VLAdapter
import com.sitamadex11.covidhelp.adapter.VolunteerListAdapter
import com.sitamadex11.covidhelp.model.VolunteerDetailsModel


class ViewVolunteerFragment : Fragment(), View.OnClickListener, VLAdapter {
    lateinit var btnAddVolunteer: FloatingActionButton
    lateinit var rvVol: RecyclerView
    lateinit var adapter: VolunteerListAdapter
    val allVolunteers = ArrayList<VolunteerDetailsModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_volunteer, container, false)
        init(view)
        click()
        addItemToList()
        return view
    }

    private fun click() {
        btnAddVolunteer.setOnClickListener(this)
    }

    private fun init(view: View) {
        btnAddVolunteer = view.findViewById(R.id.btnAddVolunteer)
        rvVol = view.findViewById(R.id.rvVolunteer)
        rvVol.layoutManager = LinearLayoutManager(context)
        adapter = VolunteerListAdapter(requireContext(), this)
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
        FirebaseFirestore.getInstance().collection("volunteers").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                for (snapshot in queryDocumentSnapshots) {
                    val name = snapshot.getString("name")
                    val state = snapshot.getString("state")
                    val district = snapshot.getString("district")
                    val phoneS: String? = snapshot.getString("phone")
                    val org = snapshot.getString("organization")
                    // val phone=Integer.parseInt(phoneS!!)
                    allVolunteers.add(VolunteerDetailsModel(name, phoneS, state, district, org))
                    // Log.d("ref_check", img.toString())
                }
                adapter.updateList(allVolunteers)
                rvVol.adapter = adapter
            }.addOnFailureListener { e ->
            Log.d("EXPLOREFRAGMENT", "onFailure: " + e.message)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(
                    requireActivity().applicationContext,
                    "permission granted.",
                    Toast.LENGTH_SHORT
                ).show();
            else
                Toast.makeText(
                    requireActivity().applicationContext,
                    "permission denied.",
                    Toast.LENGTH_SHORT
                ).show();
        }
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
}