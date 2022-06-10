package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.repository.JournalRepository

class MapsViewModel(application: Application) : ViewModel() {
    private val jRepo = JournalRepository(application)
    private val entries = jRepo.getJournalData()


}