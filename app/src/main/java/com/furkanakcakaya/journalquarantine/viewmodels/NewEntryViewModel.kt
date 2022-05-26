package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository


class NewEntryViewModel:ViewModel() {
    private val jRepo = JournalRepository()
    private val jEntry = JournalEntry(
        1,
        "Sample Title",
        "Sample Content",
        "Sample Mood",
        "26-05-2022",
        "Sample Location",
        0.0,
        0.0,
        listOf(),
        "",
    )

    fun addImages() {
    }

    fun addEntry(title: String, content: String, mood: String){
        jEntry.title = title
        jEntry.content = content
        jEntry.mood = mood
        jRepo.insertJournalEntry(jEntry)
    }

    fun addMediaContent(mediaContent: List<Uri>?) {
        if (mediaContent != null) {
            jEntry.mediaContent = mediaContent
        }
    }

}