package com.example.notetakingapp.ui

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notetakingapp.MainActivity
import com.example.notetakingapp.R
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteApplication
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.viewmodel.NoteViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment(R.layout.fragment_note), MenuProvider {

    private val noteViewModel: NoteViewModel by activityViewModels{
        val application: Application = NoteApplication()
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteAdapter: NoteAdapter

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            // Inflate the layout for this fragment
            _binding = FragmentNoteBinding.inflate(inflater, container, false)

            return binding.root
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerview()

        view.findViewById<FloatingActionButton>(R.id.fabBtn).setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.editNoteAction, null)
                )



        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)



    }



    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        noteViewModel.getAllNote().observe(viewLifecycleOwner) { note ->
            noteAdapter.submitList(note)
            updateUi(note)
        }


    }


    private fun updateUi(note: List<Note>) {
        if (note.isNotEmpty()){
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvNoNoteAvailable.visibility = View.GONE
        }else{
                binding.recyclerView.visibility = View.GONE
                binding.tvNoNoteAvailable.visibility = View.VISIBLE
        }
    }



    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }


        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }




}


