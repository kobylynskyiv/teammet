package com.kobylynskyiv.teammeet.domain.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kobylynskyiv.teammeet.data.data.enties.CompleteLoadItems
import com.kobylynskyiv.teammeet.data.db.Database
import com.kobylynskyiv.teammeet.data.db.listeners.OnCompleteLoadDatabase
import com.kobylynskyiv.teammeet.domain.enties.FoundGameItem
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FoundGameViewModel: ViewModel(), KoinComponent, OnCompleteLoadDatabase {

    val data: MutableLiveData<CompleteLoadItems> = MutableLiveData()
    private val database: Database by inject()

    fun loadData(){
        viewModelScope.launch {
            database.getValues("games", "type", "mobile", this@FoundGameViewModel)
        }
    }

    override fun onComplete(data: ArrayList<FoundGameItem>?, status: Database.Status, textError: String?) {
        this.data.postValue(CompleteLoadItems(data, status, textError))
    }
}