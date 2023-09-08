package com.yusufmendes.noteapp.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yusufmendes.noteapp.R
import com.yusufmendes.noteapp.adapter.NoteAdapter
import com.yusufmendes.noteapp.databinding.FragmentNoteListBinding
import com.yusufmendes.noteapp.model.Note


class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private lateinit var binding: FragmentNoteListBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteList: ArrayList<Note>
    private lateinit var refUsers: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteListBinding.bind(view)

        binding.noteRv.setHasFixedSize(true)
        binding.noteRv.layoutManager = LinearLayoutManager(requireContext())

        val database = FirebaseDatabase.getInstance()
        refUsers = database.getReference("note")

        noteList = ArrayList()
        noteAdapter = NoteAdapter(noteList, ::navigateToDetail,refUsers)
        binding.noteRv.adapter = noteAdapter
        getDataFromFirebase()

        binding.addNote.setOnClickListener {
            val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(null)
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun navigateToDetail(note: Note) {
        val action = NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(note)
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun getDataFromFirebase() {
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()
                for (data in snapshot.children) {
                    val note = data.getValue(Note::class.java)
                    if (note != null) {
                        noteList.add(note)
                    }
                }
                noteAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}