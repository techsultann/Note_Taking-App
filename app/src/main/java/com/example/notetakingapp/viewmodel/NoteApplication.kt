package com.example.notetakingapp.viewmodel

import android.app.Application
import com.example.notetakingapp.db.NoteDataBase
import com.example.notetakingapp.repository.NoteRepository

class NoteApplication: Application() {

    val database by lazy { NoteDataBase.createDatabase(this)}
    val repository by lazy { NoteRepository(database.getNoteDao())}

    
}