package com.yusufmendes.noteapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.yusufmendes.noteapp.databinding.NoteCardDesignBinding
import com.yusufmendes.noteapp.model.Note

class NoteAdapter(
    private val noteList: List<Note>,
    private val onClick: (Note) -> Unit,
    private val refUsers: DatabaseReference
) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: NoteCardDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                cardNoteTitle.text = note.noteTitle
                cardNoteDetail.text = note.noteDetail

                itemView.setOnClickListener {
                    onClick.invoke(note)
                }

                cardNoteDelete.setOnClickListener {
                    Snackbar.make(it, "${note.noteId} silinsin mi?", Snackbar.LENGTH_SHORT)
                        .setAction("Yes") {
                            refUsers.child(note.noteId!!).removeValue()
                        }.show()
                    notifyDataSetChanged()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            NoteCardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.bind(note)
    }
}