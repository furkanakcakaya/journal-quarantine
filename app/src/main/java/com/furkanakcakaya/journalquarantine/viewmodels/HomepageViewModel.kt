package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository

class HomepageViewModel(application: Application) : AndroidViewModel(application){
    private val TAG = "HomepageViewModel"
    private val jRepo = JournalRepository(application)
    var journalList: LiveData<List<JournalEntry>> = jRepo.getJournalData()

    fun deleteJournalEntry(id: Int) {
        jRepo.deleteJournalEntry(id)
    }

    fun insertJournalEntry(deletedJournalEntry: JournalEntry) {
        jRepo.insertJournalEntry(deletedJournalEntry)
    }

    fun searchJournal(p0: String) {
        jRepo.searchJournalEntry(p0)
    }

}