@file:OptIn(InternalCoroutinesApi::class)

package com.example.notetakingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notetakingapp.model.Note
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [Note::class], version = 1, exportSchema = false)

abstract class NoteDataBase: RoomDatabase() {


    abstract fun getNoteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDataBase? = null

        fun createDatabase(context: Context) : NoteDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "note_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }


    }
}