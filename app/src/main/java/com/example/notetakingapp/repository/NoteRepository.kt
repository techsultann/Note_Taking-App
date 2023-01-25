package com.example.notetakingapp.repository



import com.example.notetakingapp.db.NoteDao
import com.example.notetakingapp.model.Note


class NoteRepository(private val noteDao: NoteDao) {


    suspend fun addNote(note: Note) = noteDao.addNote(note)
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    fun getAllNotes()  = noteDao.getAllNotes()

}