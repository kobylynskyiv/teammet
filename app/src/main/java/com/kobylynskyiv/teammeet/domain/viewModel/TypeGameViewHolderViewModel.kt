package com.kobylynskyiv.teammeet.domain.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TypeGameViewHolderViewModel: ViewModel() {

    private var checkValue: MutableLiveData<Boolean> = MutableLiveData()
    val newCheckValue: LiveData<Boolean> get() = checkValue

    fun onClickTypeGameItemView(newCheckValue: Boolean) = checkValue.postValue(!newCheckValue)
}