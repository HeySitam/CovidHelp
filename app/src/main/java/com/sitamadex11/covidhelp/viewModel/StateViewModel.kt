package com.sitamadex11.covidhelp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitamadex11.covidhelp.model.State
import com.sitamadex11.covidhelp.repositories.StateRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class StateViewModel(val repo: StateRepository) : ViewModel() {
    val myResponse: MutableLiveData<Response<State>> = MutableLiveData()
    fun getAllCenter() {
        viewModelScope.launch {
            myResponse.value = repo.getAllState()
        }
    }
}