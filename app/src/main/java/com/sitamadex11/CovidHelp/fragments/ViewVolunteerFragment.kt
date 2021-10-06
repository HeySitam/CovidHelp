package com.sitamadex11.CovidHelp.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.adapter.VLAdapter
import com.sitamadex11.CovidHelp.adapter.VolunteerListAdapter
import com.sitamadex11.CovidHelp.model.District
import com.sitamadex11.CovidHelp.model.DistrictItems
import com.sitamadex11.CovidHelp.model.State
import com.sitamadex11.CovidHelp.model.VolunteerDetailsModel
import com.sitamadex11.CovidHelp.util.Constants
import io.github.yavski.fabspeeddial.FabSpeedDial
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.fragment_view_volunteer.*
import java.util.jar.Manifest


class ViewVolunteerFragment : Fragment(), VLAdapter, View.OnClickListener {
    lateinit var fabBtn: FabSpeedDial
    lateinit var rvVol: RecyclerView
    lateinit var adapter: VolunteerListAdapter
    lateinit var requestOueue: RequestQueue
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var dialog: AlertDialog
    lateinit var etSendMessage: TextInputEditText
    lateinit var btnSend: MaterialButton
    lateinit var btnCancel: MaterialButton
    lateinit var btnRetry: MaterialButton
    lateinit var btnExit: MaterialButton
    val allVolunteers = ArrayList<VolunteerDetailsModel>()
    val state_list = java.util.ArrayList<String>()
    val district_list = java.util.ArrayList<DistrictItems>()
    val district_name_list = java.util.ArrayList<String>()
    val CALL_PERMISSION_CODE = 1497
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_volunteer, container, false)
        val isConnected = checkConnectivity(requireContext())
        if (!isConnected) {
            val customLayout = layoutInflater
                .inflate(
                    R.layout.network_check_dialog, null
                )
            netInit(customLayout)
            val builder = AlertDialog.Builder(requireContext())
            builder.setView(customLayout)
            builder.setCancelable(false)
            val dialog = builder.create()
            btnExit.setOnClickListener {
                requireActivity().finish()
            }
            btnRetry.setOnClickListener {
                if (checkConnectivity(requireContext())) {
                    //Do some thing
                    dialog.hide()
                    init(view)
                    click()
                    callBack()
                    stateJsonParse()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Sorry!! No Internet connection found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            dialog.show()
        } else {
            //Do some thing
            init(view)
            click()
            callBack()
            stateJsonParse()
        }
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
        fabBtn.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                onNavigationItemSelected(menuItem)
                return true
            }
        })
    }

    private fun onNavigationItemSelected(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.removeVol -> {
                firebaseFirestore.collection("users").get()
                    .addOnSuccessListener { queryDocumentSnapshots ->
                        for (snapshot in queryDocumentSnapshots) {
                            val userId = snapshot.getString("uid")
                            if (userId == firebaseAuth.currentUser!!.uid) {
                                val isVol = snapshot.getString("isVol")
                                if (isVol == "0") {
                                    Toast.makeText(
                                        requireContext(),
                                        "You are not a volunteer.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    val phone = snapshot.getString("phone")
                                    firebaseFirestore.collection("volunteers").get()
                                        .addOnSuccessListener {
                                            for (volSnapShot in it) {
                                                val volPhone = volSnapShot.getString("phone")
                                                if (volPhone == phone) {
                                                    firebaseFirestore.collection("volunteers")
                                                        .document(volSnapShot.id)
                                                        .delete()
                                                        .addOnSuccessListener {
                                                            Toast.makeText(
                                                                requireContext(),
                                                                "You are no more volunteer",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    firebaseFirestore.collection("users")
                                                        .document(snapshot.id).update("isVol", "0")
                                                }
                                            }
                                        }
                                }
                            }
                        }
                    }
            }
            R.id.addVol -> {
                firebaseFirestore.collection("users").get()
                    .addOnSuccessListener { queryDocumentSnapshots ->
                        for (snapshot in queryDocumentSnapshots) {
                            val userId = snapshot.getString("uid")
                            if (userId == firebaseAuth.currentUser!!.uid) {
                                val isVol = snapshot.getString("isVol")
                                Log.d("chk_vol", isVol.toString())
                                if (isVol == "0")
                                    fragmentTransition(AddVolunteerFragment())
                                else
                                    Toast.makeText(
                                        requireContext(),
                                        "you are already a Volunteer",
                                        Toast.LENGTH_SHORT
                                    ).show()
                            }
                        }
                    }
            }
        }
    }

    private fun init(view: View) {
        fabBtn = view.findViewById(R.id.fab_speed_dial)
        rvVol = view.findViewById(R.id.rvVolunteer)
        rvVol.layoutManager = LinearLayoutManager(context)
        adapter = VolunteerListAdapter(requireContext(), this)
        requestOueue = Volley.newRequestQueue(requireContext())
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
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
        firebaseFirestore.collection("volunteers").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                for (snapshot in queryDocumentSnapshots) {
                    val name = snapshot.getString("name")
                    val state = snapshot.getString("state")
                    val district = snapshot.getString("district")
                    val phoneS: String? = snapshot.getString("phone")
                    val org = snapshot.getString("organization")
                    val desc = snapshot.getString("description")
                    // val phone=Integer.parseInt(phoneS!!)
                    if (etVolState.text.toString() == state && etVolDistrict.text.toString() == district) {
                        allVolunteers.add(
                            VolunteerDetailsModel(
                                name,
                                phoneS,
                                state,
                                district,
                                org,
                                desc
                            )
                        )
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

    override fun onCallBtnClicked(phone: String) {
        checkPermission(android.Manifest.permission.CALL_PHONE, CALL_PERMISSION_CODE)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("⚠ Alert ⚠")
        builder.setMessage("Are you want to make a call?")
        builder.setCancelable(true)
        builder.setPositiveButton(
            "Yes"
        ) { dialog, id ->
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:$phone") //change the number
            startActivity(callIntent)
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, id -> dialog.cancel() }
        val alert1 = builder.create()
        alert1.show()
    }

    override fun onMessageBtnClicked(phone: String) {
        val customLayout = layoutInflater
            .inflate(
                R.layout.dialog_message, null
            )
        msgInit(customLayout)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(customLayout)
        dialog = builder.create()
        btnCancel.setOnClickListener(this)
        dialog.show()
        btnSend.setOnClickListener {
            if (etSendMessage.text.isNullOrEmpty()) {
                etSendMessage.error = "Please enter some text message"
            } else {
                val msg = etSendMessage.text.toString()
                val sms_uri = Uri.parse("smsto:$phone")
                val sms_intent = Intent(Intent.ACTION_VIEW, sms_uri)
                sms_intent.setData(sms_uri)
                sms_intent.putExtra("sms_body", msg)
                startActivity(sms_intent);
                dialog.hide()
                etSendMessage.error = null
            }
        }

    }

    private fun msgInit(v: View?) {
        etSendMessage = v!!.findViewById(R.id.etSendMessage)
        btnCancel = v.findViewById(R.id.btnCancel)
        btnSend = v.findViewById(R.id.btnSend)
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnCancel -> {
                dialog.hide()
            }
        }
    }

    private fun netInit(v: View?) {
        btnExit = v!!.findViewById(R.id.btnExit)
        btnRetry = v.findViewById(R.id.btnRetry)
    }


    fun checkConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected != null) {
            return activeNetwork.isConnected
        } else {
            return false
        }
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
        } else {
            Toast.makeText(requireContext(), "Permission Already Granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Call Permission Granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Call Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}