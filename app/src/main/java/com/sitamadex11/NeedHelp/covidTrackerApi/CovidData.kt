package com.sitamadex11.NeedHelp.covidTrackerApi

import com.google.gson.annotations.SerializedName

class CovidData(@SerializedName("data") var data: Data, var lastRefreshed: String = "")