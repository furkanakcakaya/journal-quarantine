package com.furkanakcakaya.journalquarantine.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.furkanakcakaya.journalquarantine.entities.JournalEntry

object JournalRepository {
    private val TAG = "JournalRepository"
    private val journalData: MutableLiveData<List<JournalEntry>> = MutableLiveData()

    init {
        getAllJournals()
    }

    fun getJournalData(): LiveData<List<JournalEntry>> {
        return journalData
    }

    private fun getAllJournals() {
        val journalList = ArrayList<JournalEntry>()
        journalList.add(
            JournalEntry(
                1,
                "Journal 1",
                "This is the first journal entry",
                "Sample Mood",
                "2020-01-01",
                "Davutpaşa",
                42.5,
                41.4,
                listOf(),
                ""
            )
        )
        journalData.value = journalList
    }

    private fun orderJournals(){
        val journalList = journalData.value
        journalList?.sortedBy { it.createdAt }
        journalData.value = journalList
    }

    fun deleteJournalEntry(id: Int) {
        journalData.value = journalData.value?.filter { it.id != id }
        orderJournals()
    }

    fun insertJournalEntry(entry: JournalEntry) {
        val newJournalList = ArrayList<JournalEntry>()
        newJournalList.addAll(journalData.value!!)
        newJournalList.add(entry)
        journalData.value = newJournalList
        orderJournals()
    }

}