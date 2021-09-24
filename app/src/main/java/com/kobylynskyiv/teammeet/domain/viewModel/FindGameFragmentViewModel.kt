package com.kobylynskyiv.teammeet.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.LiveData
import com.kobylynskyiv.teammeet.R
import com.kobylynskyiv.teammeet.presenter.data.enties.TypeGameItem


class FindGameFragmentViewModel: ViewModel() {
    val data: LiveData<List<TypeGameItem>> = MutableLiveData(
        listOf(
            TypeGameItem("Мобільні ігри", R.drawable.game_mobile),
            TypeGameItem("Компьютерні ігри", R.drawable.game_computer),
            TypeGameItem("Спортивні ігри", R.drawable.game_live)
        )
    )


}