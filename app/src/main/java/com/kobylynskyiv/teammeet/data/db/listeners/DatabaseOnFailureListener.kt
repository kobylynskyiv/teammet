package com.kobylynskyiv.teammeet.data.db.listeners

import android.annotation.SuppressLint
import com.google.android.gms.tasks.OnFailureListener
import com.kobylynskyiv.teammeet.data.db.Database
import java.lang.Exception

class DatabaseOnFailureListener(private val listener: OnCompleteLoadDatabase): OnFailureListener {

    @SuppressLint("LongLogTag")
    override fun onFailure(p0: Exception) {
        listener.onComplete(null, Database.Status.ERROR, p0.toString())
    }

}