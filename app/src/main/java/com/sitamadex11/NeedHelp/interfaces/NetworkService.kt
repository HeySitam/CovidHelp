package com.sitamadex11.NeedHelp.interfaces

import com.sitamadex11.NeedHelp.model.State
import retrofit2.Response

interface NetworkService {
    suspend fun getStates(): Response<State>
}