package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import android.location.Location
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository
import com.google.android.gms.tasks.Task
import java.util.*

class NewEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val jRepo = JournalRepository(application)
    lateinit var locationTask: Task<Location>
    private val jEntry = JournalEntry(
        0,
        "Başlık ALınamadı",
        "Içerik Alınamadı",
        "Mood *",
        "Tarih Alınamadı",
        "Konum Alınamadı",
        0.0,
        0.0,
        "",
        "",
    )

    fun addEntry(title: String, content: String, mood: String){
        val calendar = Calendar.getInstance()
        jEntry.title = title
        jEntry.content = content
        jEntry.mood = mood
        jEntry.createdAt = "${calendar.get(Calendar.DAY_OF_MONTH)} " +
                "${calendar.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.ENGLISH)} " +
                "${calendar.get(Calendar.YEAR)}"
        jRepo.insertJournalEntry(jEntry)
    }

    fun setMediaContent(mediaContent: String) {
        jEntry.mediaContent = mediaContent
    }

    fun getLocation(){
        locationTask.addOnSuccessListener {
            if (it != null){
                jEntry.latitude = it.latitude
                jEntry.longitude = it.longitude
                jEntry.locationName = "Permission granted."
            }else{
                jEntry.locationName = "Permission denied."
            }
        }
    }
}