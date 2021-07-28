package com.sitamadex11.NeedHelp.model

data class State(
    val states: List<StateDetails>,
    val ttl: Int
)

data class StateDetails(
    val state_id: String,
    val state_name: String
)