package com.sitamadex11.CovidHelp.repositories

import com.sitamadex11.CovidHelp.util.Client

class StateRepository {
    suspend fun getAllState() = Client.api.getStates()
}