@file:OptIn(InternalCoroutinesApi::class)

package com.example.notetakingapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notetakingapp.model.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch


@Database(entities = [Note::class], version = 1)
abstract class NoteDataBase: RoomDatabase() {



    abstract fun getNoteDao(): NoteDao

    private class NoteDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { dataBase ->
                scope.launch {
                    populateDatabase(dataBase.getNoteDao())
                }
            }
        }

        suspend fun populateDatabase(noteDao: NoteDao) {
            noteDao.getAllNotes()
        }
    }


    companion object {


        @Volatile
        private var INSTANCE: NoteDataBase? = null


        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NoteDataBase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDataBase::class.java,
                    "note_db"
                ).addCallback(NoteDatabaseCallback(scope))
                    .build()

                INSTANCE = instance

                instance
            }
        }


        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDataBase::class.java,
            "note_db"
        ).build()
    }



}