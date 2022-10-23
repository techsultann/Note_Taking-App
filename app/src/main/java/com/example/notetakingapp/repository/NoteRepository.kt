package com.example.notetakingapp.repository

import com.example.notetakingapp.db.NoteDao
import com.example.notetakingapp.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDAO: NoteDao) {




    suspend fun addNote(note: Note) = noteDAO.addNote(note)
    suspend fun updateNote(note: Note) = noteDAO.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDAO.deleteNote(note)
    val getAllNotes : Flow<List<Note>> = noteDAO.getAllNotes()
}