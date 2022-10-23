package com.example.notetakingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapp.adapter.NoteAdapter
import com.example.notetakingapp.databinding.ActivityMainBinding
import com.example.notetakingapp.repository.NoteApplication
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.viewmodel.NoteViewModelFactory

@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration:AppBarConfiguration
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = NoteAdapter()
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        noteViewModel.getAllNotes.observe(this, Observer { notes ->
            notes?.let { adapter }
        })

        setSupportActionBar(binding.toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav) as NavHostFragment ? ?: return

        //setup action bar
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)

    }   
}