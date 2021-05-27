package com.sitamadex11.covidhelp.interfaces

import com.sitamadex11.covidhelp.model.State
import retrofit2.Response
import retrofit2.http.GET

interface NetworkService {
    suspend fun getStates(): Response<State>
}