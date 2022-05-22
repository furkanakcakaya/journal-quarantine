package com.furkanakcakaya.journalquarantine.viewmodels

import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.repository.JournalRepository

class HomepageViewModel : ViewModel(){
    private val TAG = "HomepageViewModel"
    private val jRepo = JournalRepository()

    init {
        jRepo.getAllJournals()
    }

}