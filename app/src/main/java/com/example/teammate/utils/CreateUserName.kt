package com.example.teammate.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.util.*

class CreateUserName {

    fun generateUser() : String{
        return "player" + (1..10_000).random()
    }

    fun generateUUID(): String{
        return UUID.randomUUID().toString()
    }


    fun loadUUID(context: Context) : String {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        @SuppressLint("CommitPrefEdits") val prefsEditor = sp.edit()
        var uuidCheck : String = sp.getString("UUID", "").toString()

        if(uuidCheck.isEmpty()){
            val uniqueId = generateUUID()
            prefsEditor.putString("UUID", uniqueId)
            prefsEditor.apply()
            uuidCheck = uniqueId
        }

        return uuidCheck
    }
}