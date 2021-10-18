package com.sitamadex11.CovidHelp.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sitamadex11.CovidHelp.R
import com.sitamadex11.CovidHelp.adapter.DonationListAdapter
import com.sitamadex11.CovidHelp.model.OrgDetails
import kotlinx.android.synthetic.main.donation_list_activity.*
import java.util.*


class DonationListActivity : AppCompatActivity() {
    private val list = ArrayList<OrgDetails>()
    lateinit var firestore: FirebaseFirestore
    lateinit var reference: CollectionReference
    lateinit var btnRetry: MaterialButton
    lateinit var btnExit: MaterialButton
    lateinit var loading: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConnected = checkConnectivity(this)
        if (!isConnected) {
            val customLayout = layoutInflater
                .inflate(
                    R.layout.network_check_dialog, null
                )
            msgInit(customLayout)
            val builder = AlertDialog.Builder(this)
            builder.setView(customLayout)
            builder.setCancelable(false)
            val dialog = builder.create()
            btnExit.setOnClickListener {
                finish()
            }
            btnRetry.setOnClickListener {
                if (checkConnectivity(this)) {
                    //Do some thing
                    dialog.hide()
                    setContentView(R.layout.donation_list_activity)
                    button_back.setOnClickListener { finish() }
                    firestore = FirebaseFirestore.getInstance()
                    reference = firestore.collection("donations")
                    loading = findViewById(R.id.shimmer_layout)
                    addItemToList()
                    Log.d("list_check", list.toString())


                } else {
                    Toast.makeText(this, "Sorry!! No Internet connection found", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            dialog.show()
        } else {
            //Do some thing
            setContentView(R.layout.donation_list_activity)
            button_back.setOnClickListener { finish() }
            firestore = FirebaseFirestore.getInstance()
            reference = firestore.collection("donations")
            loading = findViewById(R.id.shimmer_layout)
            addItemToList()
            Log.d("list_check", list.toString())
        }
    }

    private fun addItemToList() {
        reference.get().addOnSuccessListener { queryDocumentSnapshots ->
            for (snapshot in queryDocumentSnapshots) {
                val title = snapshot.id
                val img = snapshot.getString("img")
                val desc = snapshot.getString("desc")
                val url = snapshot.getString("url")
                list.add(OrgDetails(img, desc, url, title))
                Log.d("ref_check", img.toString())
            }
            val adapter = DonationListAdapter(list, this)
            loading.stopShimmer()
            loading.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            recycler.adapter = adapter
            recycler.set3DItem(true)
            recycler.setIntervalRatio(0.69f)
            recycler.setAlpha(true)
        }.addOnFailureListener { e ->
            Log.d("EXPLOREFRAGMENT", "onFailure: " + e.message)
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun msgInit(v: View?) {
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
}