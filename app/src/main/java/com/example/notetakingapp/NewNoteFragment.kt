package com.example.notetakingapp

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentEditNoteBinding
import com.example.notetakingapp.db.NoteDataBase
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class NewNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentEditNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<FloatingActionButton>(R.id.fabBtnDone).setOnClickListener{
            saveNote(view)

        }



        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.new_note_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        })


        }

    private fun saveNote(view: View) {

        val noteTitle = binding.etTitle.text.toString().trim()
        val noteBody = binding.etNote.text.toString().trim()

        val note = Note(0, noteTitle, noteBody)
        val replyIntent = Intent()
        if (noteBody.isNotEmpty()) {

            viewModel.addNote(note)
            Snackbar.make(
                requireView(),
            "Note Saved Successfully",
            Snackbar.LENGTH_SHORT
            ).show()

            view.findNavController().navigate(R.id.action_editNoteDestination_to_noteDestination)
        }else{
            Toast.makeText(activity, "Note is Empty", Toast.LENGTH_LONG).show()
        }




}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    }