package com.kobylynskyiv.teammeet.data.db.listeners

import com.kobylynskyiv.teammeet.data.db.Database
import com.kobylynskyiv.teammeet.domain.enties.FoundGameItem

interface OnCompleteLoadDatabase {
    fun onComplete(data: ArrayList<FoundGameItem>?, status: Database.Status, textError: String? = null)
}