package com.example.takenote.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.takenote.data.model.Note
import com.example.takenote.databinding.ItemNoteBinding

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var noteList = emptyList<Note>()

    class NotesViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(note: Note){
            binding.note = note
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NotesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteBinding.inflate(layoutInflater, parent, false)
                return NotesViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentItem = noteList[position]
        holder.bind(currentItem)
    }

    fun setData(notes: List<Note>){
        val noteDiffUtil = NoteDiffUtil(noteList, notes)
        val noteDiffResult = DiffUtil.calculateDiff(noteDiffUtil)
        this.noteList = notes
        noteDiffResult.dispatchUpdatesTo(this)
    }


}