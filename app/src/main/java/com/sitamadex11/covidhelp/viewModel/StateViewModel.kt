package com.sitamadex11.covidhelp.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.sitamadex11.covidhelp.model.CenterDetail
import com.sitamadex11.covidhelp.model.State
import com.sitamadex11.covidhelp.repositories.StateRepository
import com.sitamadex11.covidhelp.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Response

class StateViewModel(val context: Context) : ViewModel() {
    val myResponse: MutableLiveData<CenterDetail> = MutableLiveData()
//    fun getAllCenter() {
//        viewModelScope.launch {
//            myResponse.value = repo.getAllState()
//        }
         fun jsonParse(id:String,date:String): StringRequest {
            val url ="${Constants.COWIN_URL}district_id=$id&date=$date"
            return StringRequest(url, {
                Log.d("chk", it)
                val centers = Gson().fromJson(it, CenterDetail::class.java)
                myResponse.value=centers
            }, {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
            })
        }
    }
