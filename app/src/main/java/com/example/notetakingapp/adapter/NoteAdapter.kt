package com.example.notetakingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notetakingapp.databinding.NoteLayoutAdapterBinding
import com.example.notetakingapp.model.Note
import com.example.notetakingapp.ui.HomeFragmentDirections

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder( val binding: NoteLayoutAdapterBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var mlistener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setItemClickListener(clickListener: onItemClickListener) {
        mlistener = clickListener
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Note>() {

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        return NoteViewHolder(NoteLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.binding.apply {

            tvNoteTitle.text = currentNote.noteTitle
            tvNoteBody.text = currentNote.noteBody

        }
        holder.itemView.setOnClickListener { mView ->

            val directions = HomeFragmentDirections.actionNoteDestinationToUpdateNoteDestination(currentNote)
            mView.findNavController().navigate(directions)

        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /*class NoteViewHolder(itemView: View) : ViewHolder(itemView) {

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
    }*/



}