package com.sitamadex11.covidhelp.interfaces

import com.sitamadex11.covidhelp.model.State
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {
    @GET("api/v2/admin/location/states")
    suspend fun getStates(): Response<State>
}