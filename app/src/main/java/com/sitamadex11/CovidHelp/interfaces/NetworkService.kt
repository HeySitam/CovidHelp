package com.sitamadex11.CovidHelp.interfaces

import com.sitamadex11.CovidHelp.model.State
import retrofit2.Response

interface NetworkService {
    suspend fun getStates(): Response<State>
}