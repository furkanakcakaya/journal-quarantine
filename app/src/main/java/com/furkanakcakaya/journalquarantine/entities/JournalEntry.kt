package com.furkanakcakaya.journalquarantine.entities

import android.net.Uri
import java.util.*

data class JournalEntry(
    var id: Int,
    var title: String,
    var content: String,
    var mood: String,
    var createdAt: String,
    var locationName: String,
    var latitude: Double,
    var longitude: Double,
    var mediaContent: List<Uri>,
    val password: String?
)
