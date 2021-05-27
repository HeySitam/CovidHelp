package com.sitamadex11.covidhelp.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.sitamadex11.covidhelp.R
import com.sitamadex11.covidhelp.fragments.AddVolunteerFragment
import com.sitamadex11.covidhelp.fragments.ViewVolunteerFragment
import kotlinx.android.synthetic.main.donation_list_activity.*

class VolunteerActivity : AppCompatActivity() {
    lateinit var json:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)
        val fragment=ViewVolunteerFragment()
        fragmentTransaction(fragment)
        sendStateDistInfo()
    }

    private fun sendStateDistInfo() {
        FirebaseFirestore
            .getInstance()
            .collection("json")
            .get().addOnSuccessListener { queryDocumentSnapshots ->
                for (snapshot in queryDocumentSnapshots) {
                     json = snapshot.getString("state")!!
                    Log.d("state",json)
                }
                val bundle = Bundle()
                bundle.putString("state", json)
                val fragobj = AddVolunteerFragment()
                fragobj.arguments = bundle
            }.addOnFailureListener { e ->
                Log.d("EXPLOREFRAGMENT", "onFailure: " + e.message)
                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fragmentTransaction(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flVolunteer,fragment).commit()
    }
}