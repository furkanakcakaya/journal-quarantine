package com.furkanakcakaya.journalquarantine.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable
import java.util.*

@Entity(tableName = "journals")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") @NotNull var id: Int,
    @ColumnInfo(name = "title") @NotNull var title: String,
    @ColumnInfo(name = "content") @NotNull var content: String,
    @ColumnInfo(name = "mood") @NotNull var mood: String,
    @ColumnInfo(name = "created_at") @NotNull var createdAt: String,
    @ColumnInfo(name = "loc_name") @NotNull var locationName: String,
    @ColumnInfo(name = "loc_lat") @NotNull var latitude: Double,
    @ColumnInfo(name = "loc_lon") @NotNull var longitude: Double,
    @ColumnInfo(name = "media") @NotNull var mediaContent: String,
    @ColumnInfo(name = "password") @NotNull var password: String
):Serializable
