package com.sitamadex11.covidhelp.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.adapter.DonationListAdapter
import com.sitamadex11.covidhelp.model.OrgDetails
import kotlinx.android.synthetic.main.donation_list_activity.*
import java.util.*


class DonationListActivity : AppCompatActivity() {
    private val list = ArrayList<OrgDetails>()
    lateinit var firestore: FirebaseFirestore
    lateinit var reference: CollectionReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donation_list_activity)
        firestore = FirebaseFirestore.getInstance()
        reference = firestore.collection("donations")

        addItemToList()
        Log.d("list_check", list.toString())

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
            recycler.adapter = adapter
            recycler.set3DItem(true)
            recycler.setAlpha(true)
        }.addOnFailureListener { e ->
            Log.d("EXPLOREFRAGMENT", "onFailure: " + e.message)
            Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
        }
    }
}