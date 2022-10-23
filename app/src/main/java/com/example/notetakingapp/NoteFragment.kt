package com.example.notetakingapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.example.notetakingapp.databinding.FragmentNoteBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteFragment : Fragment(R.layout.fragment_note), MenuProvider {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!



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


        view.findViewById<FloatingActionButton>(R.id.fabBtn).setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.editNoteAction, null)
                )

        activity?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)



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


