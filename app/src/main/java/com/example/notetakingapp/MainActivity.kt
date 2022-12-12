package com.example.notetakingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.ActivityMainBinding
import com.example.notetakingapp.db.NoteDataBase
import com.example.notetakingapp.repository.NoteRepository
import com.example.notetakingapp.viewmodel.NoteViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration:AppBarConfiguration
    lateinit var adapter: NoteAdapter
    private lateinit var dataBase: NoteDataBase

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav) as NavHostFragment ? ?: return

        //setup action bar
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupViewModel()


    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.getAllNotes.observe(this) { list ->
            list?.let {
                adapter.submitList(list)
            }
        }


    }


}
