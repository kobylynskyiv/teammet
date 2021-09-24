package com.kobylynskyiv.teammeet.presenter

import android.app.Application
import com.kobylynskyiv.teammeet.data.db.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MyApp)
            modules(listOf(userModule, databaseModule))
        }
    }

}