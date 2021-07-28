package com.sitamadex11.NeedHelp.covidTrackerApi

import com.google.gson.annotations.SerializedName

class Data(
    @SerializedName("summary")
    var summary: Summery,
    var regional: List<Regional>
)