package com.sitamadex11.CovidHelp.util

import com.sitamadex11.CovidHelp.interfaces.NetworkService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.STATE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(NetworkService::class.java)
}