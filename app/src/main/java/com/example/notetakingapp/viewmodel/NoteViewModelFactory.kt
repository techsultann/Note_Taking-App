package com.example.notetakingapp.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.notetakingapp.repository.NoteRepository

class NoteViewModelFactory(

    val app: Application,
    private val noteRepository: NoteRepository
): ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return NoteViewModel(app, noteRepository) as T
    }

    }