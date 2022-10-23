package com.example.notetakingapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "notes")

data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteTitle: String,
    val noteBody: String

    )