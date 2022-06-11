package com.furkanakcakaya.journalquarantine.viewmodels

import android.app.Application
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.furkanakcakaya.journalquarantine.R
import com.furkanakcakaya.journalquarantine.entities.JournalEntry
import com.furkanakcakaya.journalquarantine.repository.JournalRepository
import com.google.android.gms.tasks.Task
import java.io.File
import java.io.IOException
import java.util.*


class NewEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val jRepo = JournalRepository(application)
    lateinit var locationTask: Task<Location>
    private val jEntry = JournalEntry(
        0,
        "",
        "",
        "normal",
        application.getString(R.string.date_error),
        application.getString(R.string.location_error),
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
        copyFileToAppDir()
        jRepo.insertJournalEntry(jEntry)
    }

    private fun copyFileToAppDir() {
        if (jEntry.mediaContent.isNotBlank()){
            val file = getApplication<Application>().contentResolver.openInputStream(Uri.parse(jEntry.mediaContent))
            val dir = File(getApplication<Application>().filesDir, "images")
            val newFile = File(dir, "${jEntry.id}-${jEntry.title}.jpg")
            try {
                newFile.createNewFile()
                file?.copyTo(newFile.outputStream())
                jEntry.mediaContent = newFile.absolutePath
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
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
                jEntry.locationName = getApplication<Application>().getString(R.string.permission_denied)
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
            getApplication<Application>().getString(R.string.location_not_found)
        }
    }

    fun setMood(mood: String) {
        jEntry.mood = mood
    }
}