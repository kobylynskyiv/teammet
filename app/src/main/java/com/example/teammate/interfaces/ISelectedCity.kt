package com.example.teammate.interfaces

import android.os.Parcelable
import android.view.View
import com.example.teammate.ui.city.adapter.CityItems

interface ISelectedCity : Parcelable {
    fun selectedCity(items: CityItems, view: View?)
}