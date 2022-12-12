package com.example.notetakingapp.repository

import com.example.notetakingapp.db.NoteDao
import com.example.notetakingapp.db.NoteDataBase
import com.example.notetakingapp.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val db: NoteDataBase) {




    suspend fun addNote(note: Note) = db.getNoteDao().addNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    val getAllNotes : Flow<List<Note>> = db.getNoteDao().getAllNotes()
}