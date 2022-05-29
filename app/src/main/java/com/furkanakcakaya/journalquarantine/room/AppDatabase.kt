package com.furkanakcakaya.journalquarantine.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.furkanakcakaya.journalquarantine.entities.JournalEntry


@Database(
    entities = [ JournalEntry::class ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun journalDao(): JournalDao
    companion object{
        private const val DATABASE_NAME = "journals.sqlite"
        var instance: AppDatabase? = null
        fun getInstance(context: Context) : AppDatabase{
            if (instance == null){
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME)
                        .createFromAsset(DATABASE_NAME).build()
                }
            }
            return instance!!
        }
    }
}