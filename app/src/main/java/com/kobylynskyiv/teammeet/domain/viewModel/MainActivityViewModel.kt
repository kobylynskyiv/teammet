package com.kobylynskyiv.teammeet.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    private val mutableTitle = MutableLiveData<String>()
    val title: LiveData<String> get() = mutableTitle
    fun updateActionBarTitle(newTitle: String) = mutableTitle.postValue(newTitle)
}