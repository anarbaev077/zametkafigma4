package com.example.zametkafigma4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zametkafigma4.databinding.NotesItemBinding

class NotesAdapter (
    private val onDeleteNoteClick: (index:Int) -> Unit,
    private val reportToDetails: (notes: NotesModel) -> Unit
): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notesList = mutableListOf<NotesModel>()

    fun updateList(notesList: List<NotesModel>){
            this.notesList.clear()
            this.notesList.addAll(notesList)
            notifyDataSetChanged()
        }

    inner class NotesViewHolder(
            private val binding: NotesItemBinding,
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(notesModel: NotesModel) {
                binding.tvNoteTitle.text = notesModel.noteTitle
                binding.tvNoteOverview.text = notesModel.noteTitle
                binding.deleteNoteBtn.setOnClickListener{
                    onDeleteNoteClick.invoke(notesList.indexOf(notesModel))
                }
            }
        }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int,
        ): NotesViewHolder {
            val binding = NotesItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.notes_item,
                    parent,
                    false)
            )
            return NotesViewHolder(binding)
        }

    override fun getItemCount() = notesList.size


    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int,
    )   {
        holder.bind(notesList[position])
    }
}