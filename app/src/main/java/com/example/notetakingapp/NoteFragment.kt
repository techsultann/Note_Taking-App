package com.example.notetakingapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFragment : Fragment(R.layout.fragment_note), MenuProvider {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()
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
        setupViewModel()



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


        activity?.let {
            viewModel.getAllNotes.observe(viewLifecycleOwner) { note ->
                noteAdapter.submitList(note)
                updateUi(note)
            }
        }


    }

    private fun updateUi(note: List<Note>) {
        if (note.isNotEmpty()){
            binding.recyclerView.visibility = WView.VISIBLE
            binding.tvNoNoteAvailable.visibility = View.GONE
        }else{
                binding.recyclerView.visibility = View.GONE
                binding.tvNoNoteAvailable.visibility = View.VISIBLE
        }
    }

    private fun setupViewModel() {

        viewModel.getAllNotes.observe(viewLifecycleOwner) { list ->
            list?.let {
                noteAdapter.submitList(list)
            }
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


