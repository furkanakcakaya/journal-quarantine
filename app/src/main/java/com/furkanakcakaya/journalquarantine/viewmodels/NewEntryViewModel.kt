package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.repository.JournalRepository


class NewEntryViewModel:ViewModel() {
    private val jRepo = JournalRepository()

    fun addEntry(entry: String, content: String, mood: String){
        jRepo.addEntry(entry, content, mood)
    }

    fun addImages() {
    }
}