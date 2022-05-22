package com.furkanakcakaya.journalquarantine.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.furkanakcakaya.journalquarantine.entities.JournalEntry

class JournalRepository {

    private val journalData: MutableLiveData<List<JournalEntry>> = MutableLiveData()

    fun getJournalData(): LiveData<List<JournalEntry>> {
        return journalData
    }

    fun getAllJournals() {
        val journalList = ArrayList<JournalEntry>()
        journalList.add(
            JournalEntry(
                1,
                "Journal 1",
                "This is the first journal entry",
                "2020-01-01",
                "2020-01-01"
            )
        )
        journalList.add(
            JournalEntry(
                2,
                "Journal 2",
                "This is the second journal entry",
                "2020-01-02",
                "2020-01-02"
            )
        )
        journalList.add(
            JournalEntry(
                3,
                "Journal 3",
                "This is the third journal entry",
                "2020-01-03",
                "2020-01-03"
            )
        )
        journalData.value = journalList
    }
}