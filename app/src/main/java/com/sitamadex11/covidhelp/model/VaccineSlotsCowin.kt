package com.sitamadex11.covidhelp.model

import com.google.gson.annotations.SerializedName

data class SessionItem(
    @SerializedName("date")
    val date: String,
    @SerializedName("available_capacity")
    val totalCapacity: Int,
    @SerializedName("min_age_limit")
    val minAgeLimit: Int,
    @SerializedName("vaccine")
    val vaccine: String,
    @SerializedName("slots")
    val slots: List<String>,
    @SerializedName("available_capacity_dose1")
    val dose1: Int,
    @SerializedName("available_capacity_dose2")
    val dose2: Int
)

data class CenterItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("pincode")
    val pinCode: Int,
    @SerializedName("fee_type")
    val feeType: String,
    @SerializedName("sessions")
    val sessions: List<SessionItem>
)

data class CenterDetail(
    @SerializedName("centers")
    val centers: List<CenterItem>
)