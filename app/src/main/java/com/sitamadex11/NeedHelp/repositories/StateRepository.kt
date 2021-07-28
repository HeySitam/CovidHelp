package com.sitamadex11.NeedHelp.repositories

import com.sitamadex11.NeedHelp.util.Client

class StateRepository {
    suspend fun getAllState() = Client.api.getStates()
}