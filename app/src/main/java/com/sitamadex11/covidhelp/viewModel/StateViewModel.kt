package com.sitamadex11.covidhelp.viewModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.toolbox.StringRequest
import com.sitamadex11.covidhelp.model.State
import com.sitamadex11.covidhelp.repositories.StateRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class StateViewModel(val context: Context) : ViewModel() {
    val myResponse: MutableLiveData<Response<State>> = MutableLiveData()
//    fun getAllCenter() {
//        viewModelScope.launch {
//            myResponse.value = repo.getAllState()
//        }
         fun jsonParse(): StringRequest {
            val url = "https://cdn-api.co-vin.in/api/v2/admin/location/districts/36"
            return StringRequest(url, {
                Log.d("chk", it)
            }, {
                Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
            })
        }
    }
