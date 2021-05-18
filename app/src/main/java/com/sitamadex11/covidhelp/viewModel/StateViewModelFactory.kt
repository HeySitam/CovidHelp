package com.sitamadex11.covidhelp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sitamadex11.covidhelp.repositories.StateRepository

class StateViewModelFactory(val repository: StateRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StateViewModel(repository) as T
    }
}