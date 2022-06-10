package com.furkanakcakaya.journalquarantine.viewmodels

import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.entities.JournalEntry

class UpdateEntryViewModel : ViewModel() {
    fun updateEntry(id: Int, title: String, content: String) {
    }

    fun setMood(mood: String) {
        journalEntry.mood = mood
    }

    fun setMediaContent(media: String) {
        journalEntry.mediaContent = media
    }

    lateinit var journalEntry: JournalEntry

}