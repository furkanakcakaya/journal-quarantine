package com.furkanakcakaya.journalquarantine.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository

class HomepageViewModel : ViewModel(){
    private val TAG = "HomepageViewModel"
    private val jRepo = JournalRepository()
    var journalList: LiveData<List<JournalEntry>> = jRepo.getJournalData()

}