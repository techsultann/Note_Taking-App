package com.example.notetakingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavBackStackEntry.Companion.create
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notetakingapp.R
import com.example.notetakingapp.databinding.NoteLayoutAdapterBinding
import com.example.notetakingapp.model.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private lateinit var binding : NoteLayoutAdapterBinding

    class NoteViewHolder(itemBinding: NoteLayoutAdapterBinding) :
        ViewHolder(itemBinding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        binding = NoteLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)

        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemView.apply {

            binding.tvNoteTitle.text = currentNote.noteTitle
            binding.tvNoteBody.text = currentNote.noteBody
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private val differCallBack =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    private val differ = AsyncListDiffer(this, differCallBack)


}