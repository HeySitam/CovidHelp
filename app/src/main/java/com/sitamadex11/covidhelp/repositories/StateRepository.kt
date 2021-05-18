package com.sitamadex11.covidhelp.repositories

import com.sitamadex11.covidhelp.util.Client

class StateRepository {
    suspend fun getAllState() = Client.api.getStates()
}