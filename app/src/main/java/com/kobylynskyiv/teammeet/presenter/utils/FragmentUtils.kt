package com.kobylynskyiv.teammeet.presenter.utils

import android.app.Activity


interface FragmentUtils {
    fun getTitle() : String
    fun onBackPressed(activity: Activity?) {
       activity?.onBackPressed()
    }
}