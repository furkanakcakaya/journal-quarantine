package com.furkanakcakaya.journalquarantine.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.room.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalRepository(var application: Application) {
    private val journalData: MutableLiveData<List<JournalEntry>> = MutableLiveData()
    private var db: AppDatabase = AppDatabase.getInstance(application)

    init {
        loadJournals()
    }

    fun getJournalData(): LiveData<List<JournalEntry>> {
        return journalData
    }

    fun loadJournals() {
        CoroutineScope(Dispatchers.Main).launch {
            journalData.value = db.journalDao().getAllJournals()
        }
    }

    fun insertJournalEntry(entry: JournalEntry) {
        CoroutineScope(Dispatchers.Main).launch {
            db.journalDao().insertJournal(entry)
            loadJournals()
        }
    }

    fun deleteJournalEntry(id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            db.journalDao().deleteJournal(id)
            loadJournals()
        }
    }

    fun updateJournalEntry(id: Int,title: String, content: String, mood: String, media: String,password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            db.journalDao().updateJournal(id,title,content,mood,media,password)
            loadJournals()
        }
    }

    fun searchJournalEntry(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            journalData.value = db.journalDao().searchJournal(query)
        }
    }

}