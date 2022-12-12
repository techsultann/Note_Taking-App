package com.example.notetakingapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
): AndroidViewModel(app) {



    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    val getAllNotes : LiveData<List<Note>> = noteRepository.getAllNotes.asLiveData()
}