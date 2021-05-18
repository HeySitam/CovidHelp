package com.sitamadex11.covidhelp.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.sitamadex11.covidhelp.model.State


class DemoTestingActivity : AppCompatActivity() {
    lateinit var json: String
    lateinit var firestore: FirebaseFirestore
    lateinit var reference: CollectionReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        reference = firestore.collection("json")

        addItemToList()

    }

    private fun addItemToList() {
        reference
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                Log.d("chk", task.toString())
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        json = document.getString("state")!!
                        val state = Gson().fromJson(json, State::class.java)
                        Log.d("chk", state.toString())
                    }
                } else {
                    Log.w("chk", "Error getting documents.", task.exception)
                }
            })
    }
}