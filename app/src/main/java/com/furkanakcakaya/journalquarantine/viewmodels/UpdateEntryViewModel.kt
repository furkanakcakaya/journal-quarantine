package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository

class UpdateEntryViewModel(application: Application) : AndroidViewModel(application){
    val jRepo = JournalRepository(application)

    fun updateEntry(id: Int, title: String, content: String) {
        jRepo.updateJournalEntry(id, title, content, journalEntry.mood, journalEntry.mediaContent, journalEntry.password)
    }

    fun setMood(mood: String) {
        journalEntry.mood = mood
    }

    fun setMediaContent(media: String) {
        journalEntry.mediaContent = media
    }

    lateinit var journalEntry: JournalEntry

}