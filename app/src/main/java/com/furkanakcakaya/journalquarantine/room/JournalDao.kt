package com.furkanakcakaya.journalquarantine.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furkanakcakaya.journalquarantine.entities.JournalEntry

@Dao
interface JournalDao {

    @Query("SELECT * FROM JOURNALS")
    suspend fun getAllJournals(): List<JournalEntry>

    @Insert
    suspend fun insertJournal(journalEntry: JournalEntry)

    @Query("DELETE FROM JOURNALS WHERE id = :id")
    suspend fun deleteJournal(id: Int)

    @Query("SELECT * FROM JOURNALS WHERE id = :id")
    suspend fun getJournal(id: Int) : JournalEntry

    @Query("UPDATE JOURNALS SET title=:title, content=:content, mood =:mood, media=:media, password=:password WHERE id = :id")
    suspend fun updateJournal(id: Int,title: String, content: String, mood: String, media: String,password: String)

    @Query("SELECT * FROM JOURNALS WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' OR loc_name LIKE '%' || :query || '%'")
    suspend fun searchJournal(query: String) : List<JournalEntry>

}