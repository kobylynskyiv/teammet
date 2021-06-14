package com.example.teammate.ui.find.adapter

import com.example.teammate.ui.city.adapter.CityItems

class FoundItems(
    var city: HashMap<*,*>,
    var comments: String = "",
    var createdId: String = "",
    var date: String = "",
    var gameId: String = "",
    var location: String = "",
    var members: ArrayList<MembersGames>,
    var sport: String = "",
    var title: String = ""
)