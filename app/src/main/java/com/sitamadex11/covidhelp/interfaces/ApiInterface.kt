package com.sitamadex11.covidhelp.interfaces

import com.sitamadex11.covidhelp.covidTrackerApi.CovidData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @get:GET("latest")
    val covidData: Call<CovidData?>?

    companion object {
        const val BASE_URL = "https://api.rootnet.in/covid19-in/stats/"
    }

}