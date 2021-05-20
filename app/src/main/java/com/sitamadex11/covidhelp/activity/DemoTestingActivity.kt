package com.sitamadex11.covidhelp.activity


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class DemoTestingActivity : AppCompatActivity() {
    lateinit var json: String
    lateinit var firestore: FirebaseFirestore
    lateinit var reference: CollectionReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonParse())
    }

    private fun jsonParse(): StringRequest {
        val url = "https://cdn-api.co-vin.in/api/v2/admin/location/districts/36"
        return StringRequest(url, {
            Log.d("chk", it)
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        })
    }

}