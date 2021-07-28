package com.sitamadex11.NeedHelp.activity


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.sitamadex11.NeedHelp.model.CenterDetail


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
        val url =
            "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=725&date=23-05-2021"
        return StringRequest(url, {
            val chk_class = Gson().fromJson(it, CenterDetail::class.java)
            Log.d("chk_json", chk_class.toString())
        }, {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        })
    }

}