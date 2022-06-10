package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.furkanakcakaya.journalquarantine.repository.JournalRepository

class MapsViewModel(application: Application) :  AndroidViewModel(application) {
    private val jRepo = JournalRepository(application)
    val entries = jRepo.getJournalData()


}