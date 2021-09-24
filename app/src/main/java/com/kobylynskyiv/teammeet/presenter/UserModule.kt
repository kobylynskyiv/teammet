package com.kobylynskyiv.teammeet.presenter

import com.google.firebase.firestore.auth.User
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val userModule = module {
    //single { User(androidContext()) }
}