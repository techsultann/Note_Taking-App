package com.example.notetakingapp.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapp.R
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.FragmentNoteBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.viewmodel.NoteApplication
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.viewmodel.NoteViewModelFactory
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(R.layout.fragment_note), MenuProvider, SearchView.OnQueryTextListener {

    private val noteViewModel: NoteViewModel by viewModels{
        val application = activity?.applicationContext
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
        swipeToDelete()

        // Navigates to edit note fragment
        binding.fabBtn.setOnClickListener {
            findNavController().navigate(R.id.editNoteAction, null)
        }


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }

    // Setting up the recycler view
    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Add the list of notes in the view model to the recycler view
        noteViewModel.getAllNote().observe(viewLifecycleOwner) { note ->
            noteAdapter.differ.submitList(note)
            updateUi(note)
        }


    }


    // This updates the UI in the fragment to show the list of notes
    private fun updateUi(note: List<Note>) {
        if (note.isNotEmpty()){
            binding.recyclerView.visibility = View.VISIBLE
            binding.tvNoNoteAvailable.visibility = View.GONE
        }else{
                binding.recyclerView.visibility = View.GONE
                binding.tvNoNoteAvailable.visibility = View.VISIBLE
        }
    }

    private fun swipeToDelete() {

        // Creating a swipe to delete method for the Rv
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                // This is called when we swipe our item to right direction
                // and also to get the position of item
                val position = viewHolder.adapterPosition
                val note = noteAdapter.differ.currentList[position]

                // Remove or delete the item from Rv and Database
                noteViewModel.deleteNote(note)

                //This displays the snack bar action
                Snackbar.make(view!!, "Note deleted successfully", Snackbar.LENGTH_SHORT).apply {
                    setAction("UNDO") {
                        noteViewModel.addNote(note)
                    }
                    show()
                }

            }
        }

        //This attach the item touch helper to the recycler view
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerView)
        }
    }



    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.search_menu, menu)
            val menuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = true
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {

            R.id.menu_search ->{

            }
        }
        return true
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }


    private fun searchNote(query: String?) {

        val searchQuery = "%$query%"
        noteViewModel.searchNote(searchQuery).observe(this) { list ->

            noteAdapter.differ.submitList(list)
        }
    }




        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }



}


