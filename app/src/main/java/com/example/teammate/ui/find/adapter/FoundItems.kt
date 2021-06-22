package com.example.teammate.ui.find.adapter

import java.io.Serializable

class FoundItems(
    var city: HashMap<*,*>?,
    var comment: String = "",
    var creatorId: String = "",
    var date: com.google.firebase.Timestamp,
    var gameId: String = "",
    var location: String = "",
    var members: ArrayList<String>,
    var sport: String = "",
    var title: String = ""
): Serializable