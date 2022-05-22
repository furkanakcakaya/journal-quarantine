package com.furkanakcakaya.journalquarantine.entities

data class JournalEntry(
    var id: Int,
    var title: String,
    var content: String,
    var createdAt: String,
    var locationName: String,
    var latitude: Double,
    var longitude: Double,
    val password: String?
)
