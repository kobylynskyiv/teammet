package com.kobylynskyiv.teammeet.presenter.interfaces

import android.os.Parcelable
import android.view.View
import com.kobylynskyiv.teammeet.presenter.data.enties.CityItems

interface ISelectedCity : Parcelable {
    fun selectedCity(items: CityItems, view: View?)
}