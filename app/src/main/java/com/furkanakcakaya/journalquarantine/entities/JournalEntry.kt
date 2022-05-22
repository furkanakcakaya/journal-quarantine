package com.furkanakcakaya.journalquarantine.entities

data class JournalEntry(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: String,
    val password: String?
)
