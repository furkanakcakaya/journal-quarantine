package com.furkanakcakaya.journalquarantine.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.furkanakcakaya.journalquarantine.entities.JournalEntry

class JournalRepository {
    private val journalData: MutableLiveData<List<JournalEntry>> = MutableLiveData()

    init {
        getAllJournals()
    }

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
                "Davutpaşa",
                42.5,
                41.4,
                ""
            )
        )
        journalList.add(
            JournalEntry(
                2,
                "Journal 2",
                "This is the second journal entry",
                "2020-01-02",
                "Davutpaşa",
                42.5,
                41.4,
                ""
            )
        )
        journalList.add(
            JournalEntry(
                3,
                "Journal 3",
                "This is the third journal entry",
                "2020-01-03",
                "Davutpaşa",
                42.5,
                41.4,
                ""
            )
        )
        journalData.value = journalList
    }
}