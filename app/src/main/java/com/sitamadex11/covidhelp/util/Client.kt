package com.sitamadex11.covidhelp.util

import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    private val okHttpClient = OkHttpClient()

    private val request = Request.Builder()
        .url("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict?district_id=725&date=13-05-2021")
        .build()

    val api = okHttpClient.newCall(request)

}