package com.example.notetakingapp.repository

import android.app.Application
import com.example.notetakingapp.db.NoteDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())


    val database by lazy { NoteDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.getNoteDao()) }
}