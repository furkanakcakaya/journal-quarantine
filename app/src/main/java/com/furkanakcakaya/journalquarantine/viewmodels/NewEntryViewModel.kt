package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.util.*


class NewEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val jRepo = JournalRepository(application)
    lateinit var locationTask: Task<Location>
    private val jEntry = JournalEntry(
        0,
        "Başlık ALınamadı",
        "Içerik Alınamadı",
        "normal",
        "Tarih Alınamadı",
        "Konum Alınamadı",
        0.0,
        0.0,
        "",
        "",
    )

    fun addEntry(title: String, content: String){
        val calendar = Calendar.getInstance()
        jEntry.title = title
        jEntry.content = content
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
                jEntry.locationName = getAddress(it.latitude, it.longitude)

            }else{
                jEntry.locationName = "Permission denied."
            }
        }
    }

    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(getApplication(), Locale.getDefault())
        return try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val obj = addresses[0]
            obj.subAdminArea
        } catch (e: IOException) {
            e.printStackTrace()
            "Location not found"
        }
    }

    fun setMood(mood: String) {
        jEntry.mood = mood
    }
}