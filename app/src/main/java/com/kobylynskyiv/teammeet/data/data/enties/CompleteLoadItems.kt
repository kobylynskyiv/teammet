package com.kobylynskyiv.teammeet.data.data.enties

import com.kobylynskyiv.teammeet.data.db.Database
import com.kobylynskyiv.teammeet.domain.enties.FoundGameItem

data class CompleteLoadItems(
    val items : ArrayList<FoundGameItem>?,
    val status: Database.Status,
    val textError: String?
    )