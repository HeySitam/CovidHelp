package com.sitamadex11.CovidHelp.covidTrackerApi

import com.google.gson.annotations.SerializedName

class Data(
    @SerializedName("summary")
    var summary: Summery,
    var regional: List<Regional>
)