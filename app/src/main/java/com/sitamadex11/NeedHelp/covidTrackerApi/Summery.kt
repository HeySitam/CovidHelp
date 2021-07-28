package com.sitamadex11.NeedHelp.covidTrackerApi

import com.google.gson.annotations.SerializedName

class Summery(
    @SerializedName("total") var total: String,
    @SerializedName("discharged") var discharged: String,
    @SerializedName("deaths") var deaths: String
)