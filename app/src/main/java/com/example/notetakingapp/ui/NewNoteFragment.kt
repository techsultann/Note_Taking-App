package com.example.notetakingapp.ui

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notetakingapp.R
import com.example.notetakingapp.databinding.FragmentEditNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteApplication
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.viewmodel.NoteViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Formatter


class NewNoteFragment : Fragment(), MenuProvider {

    private val noteViewModel: NoteViewModel by viewModels{
        val application = activity?.applicationContext
        NoteViewModelFactory((application as NoteApplication).repository)
    }
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentEditNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        val formatted = currentDate.format(formatter)

        binding.tvDateTime.text =  formatted


        binding.fabBtnDone.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                saveNote()
            }
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)




        /*val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.new_note_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.saveMenu -> {
                        saveNote(view)
                    }
                }

                return true
            }

        })*/


        }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.new_note_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote()

            }
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNote() {

        val noteTitle = binding.etTitle.text.toString().trim()
        val noteBody = binding.etNote.text.toString().trim()
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        val formatted = currentDate.format(formatter)

        val note = Note(0, noteTitle, noteBody, formatted)

        if (noteTitle.isNotEmpty()) {

            noteViewModel.addNote(note)
            Snackbar.make(
                requireView(),
            "Note Saved Successfully",
            Snackbar.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_editNoteDestination_to_noteDestination, null)
        }else{

            Toast.makeText(activity, "Note title must not be empty", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_editNoteDestination_to_noteDestination, null)
        }




}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}