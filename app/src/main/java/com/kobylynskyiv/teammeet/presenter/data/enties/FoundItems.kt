package com.kobylynskyiv.teammeet.presenter.data.enties

import com.google.firebase.Timestamp
import java.io.Serializable

class FoundItems(
    var city: HashMap<*,*>?,
    var comment: String = "",
    var creatorId: String = "",
    var date: Timestamp,
    var gameId: String = "",
    var location: String = "",
    var members: ArrayList<String>,
    var sport: String = "",
    var title: String = ""
): Serializable