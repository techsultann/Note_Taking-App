package com.example.notetakingapp.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notetakingapp.R
import com.example.notetakingapp.databinding.FragmentNoteBinding
import com.example.notetakingapp.databinding.FragmentUpdateNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteApplication
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.viewmodel.NoteViewModelFactory
import com.google.android.material.snackbar.Snackbar


class UpdateNoteFragment : Fragment(), MenuProvider {

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
                findNavController().navigate(R.id.action_updateNoteDestination_to_noteDestination)
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }


    private fun deleteNote() {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Delete Note")
        builder.setMessage("Are you sure you want to delete the note?")
        builder.setPositiveButton("DELETE", DialogInterface.OnClickListener{ dialog, id ->
            noteViewModel.deleteNote(currentNote)
            Snackbar.make(requireView(), "Deleted Successfully", Snackbar.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateNoteDestination_to_noteDestination)
        })

        builder.setNegativeButton("CANCEL", null)
            builder.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.update_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
            }
        }
        return true
    }





    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}