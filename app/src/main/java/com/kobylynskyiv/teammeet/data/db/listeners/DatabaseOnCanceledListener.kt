package com.kobylynskyiv.teammeet.data.db.listeners

import com.google.android.gms.tasks.OnCanceledListener
import com.kobylynskyiv.teammeet.data.db.Database

class DatabaseOnCanceledListener(private val listener: OnCompleteLoadDatabase) : OnCanceledListener {

    override fun onCanceled() {
        listener.onComplete(null, Database.Status.ERROR, "Database download was canceled by user")
    }
}