package com.example.notetakingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.LocaleListCompat.create
import androidx.navigation.NavBackStackEntry.Companion.create
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.notetakingapp.R
import com.example.notetakingapp.databinding.NoteLayoutAdapterBinding
import com.example.notetakingapp.model.Note
import org.w3c.dom.Text

class NoteAdapter: ListAdapter<Note, NoteAdapter.NoteViewHolder>(NotesComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder.create(parent)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = getItem(position)

        holder.bind(currentNote.noteBody)
        holder.bind(currentNote.noteTitle)
        holder.bind(currentNote.id.toString())

    }

    class NoteViewHolder(itemView: View) : ViewHolder(itemView) {

        private val noteTitleView: TextView = itemView.findViewById(R.id.tvNoteTitle)
        private val noteBodyView: TextView = itemView.findViewById(R.id.tvNoteBody)

        fun bind(text: String?) {
            noteTitleView.text = text
            noteBodyView.text = text
        }

        companion object {
            fun create(parent: ViewGroup) : NoteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.note_layout_adapter, parent, false)
                return NoteViewHolder(view)
            }
        }
    }



    class NotesComparator : DiffUtil.ItemCallback<Note>() {

            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }




}