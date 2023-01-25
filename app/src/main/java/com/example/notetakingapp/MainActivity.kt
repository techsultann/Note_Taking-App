package com.example.notetakingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.notetakingapp.databinding.ActivityMainBinding
import com.example.notetakingapp.viewmodel.NoteApplication
import com.example.notetakingapp.viewmodel.NoteViewModel
import com.example.notetakingapp.viewmodel.NoteViewModelFactory


class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val noteRepository = NoteRepository(NoteDataBase.createDatabase(this))
        val noteViewModelFactory = NoteViewModelFactory(application, noteRepository)

        viewModel = ViewModelProvider(this, noteViewModelFactory)[NoteViewModel::class.java]*/


        setSupportActionBar(binding.toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav) as NavHostFragment ? ?: return

        //setup action bar
        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)




    }



}
