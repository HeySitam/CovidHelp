package com.sitamadex11.covidhelp.worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.sitamadex11.covidhelp.model.CenterDetail
import com.sitamadex11.covidhelp.util.Constants

class VaccineStatusWorker(val context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {
        val cnt = checkAvailability()
        Log.d("chk_count", "Working")
        return Result.success()
    }

    private fun checkAvailability(): Int {
        val id = "725"
        val date = "15-06-2021"
        lateinit var centers: CenterDetail
        var chk: Int = 0
        val url = "${Constants.COWIN_URL}district_id=$id&date=$date"
        StringRequest(url, {
            Log.d("chk", it)
            centers = Gson().fromJson(it, CenterDetail::class.java)
        }, {
            Toast.makeText(applicationContext, "something went wrong", Toast.LENGTH_SHORT).show()
        })
        for (i in centers.centers.indices) {
            if (centers.centers[i].sessions[0].dose1 > 0 || centers.centers[i].sessions[0].dose2 > 0) chk++
        }
        return chk
    }
}
