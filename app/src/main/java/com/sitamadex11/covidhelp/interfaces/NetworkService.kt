package com.sitamadex11.covidhelp.interfaces

import com.sitamadex11.covidhelp.model.State
import retrofit2.Response

interface NetworkService {
    suspend fun getStates(): Response<State>
}