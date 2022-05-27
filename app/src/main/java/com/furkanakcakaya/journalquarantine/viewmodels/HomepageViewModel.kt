package com.furkanakcakaya.journalquarantine.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository
import com.furkanakcakaya.journalquarantine.utils.SwipeGesture
import com.google.android.material.snackbar.Snackbar

class HomepageViewModel : ViewModel(){
    private val TAG = "HomepageViewModel"
    private val jRepo = JournalRepository
    var journalList: LiveData<List<JournalEntry>> = jRepo.getJournalData()

    fun deleteJournalEntry(id: Int) {
        jRepo.deleteJournalEntry(id)
    }

    fun insertJournalEntry(deletedJournalEntry: JournalEntry) {
        jRepo.insertJournalEntry(deletedJournalEntry)
    }

}