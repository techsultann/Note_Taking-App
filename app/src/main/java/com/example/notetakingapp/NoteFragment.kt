package com.example.notetakingapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notetakingapp.databinding.FragmentNoteBinding

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

        binding.fabBtn.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_editNoteFragment, null)
        }

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


