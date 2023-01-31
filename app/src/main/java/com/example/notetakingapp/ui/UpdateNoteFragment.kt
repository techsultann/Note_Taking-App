package com.example.notetakingapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notetakingapp.R
import com.example.notetakingapp.databinding.FragmentNoteBinding
import com.example.notetakingapp.databinding.FragmentUpdateNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteApplication
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.viewmodel.NoteViewModelFactory


class UpdateNoteFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels{
        val application = activity?.applicationContext
        NoteViewModelFactory((application as NoteApplication).repository)
    }
    private var _binding: FragmentUpdateNoteBinding? =  null
    private val binding get() = _binding!!
    val safeArgs: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentNote = safeArgs.note!!

        binding.etUpdateTitle.setText(currentNote.noteTitle)
        binding.etUpdateNote.setText(currentNote.noteBody)

        binding.updateFabBtn.setOnClickListener{

            val title = binding.etUpdateTitle.text.toString()
            val noteBody = binding.etUpdateNote.text.toString()

            if(title.isNotBlank()) {

                val note = Note(currentNote.id, title, noteBody)
                noteViewModel.updateNote(note)
                findNavController().navigate(R.id.action_updateNoteDestination_to_noteDestination)
            }else{
                Toast.makeText(activity, "Note title must not be empty", Toast.LENGTH_LONG).show()
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}