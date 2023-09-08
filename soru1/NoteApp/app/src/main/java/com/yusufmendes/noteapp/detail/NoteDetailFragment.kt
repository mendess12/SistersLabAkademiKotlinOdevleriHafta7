package com.yusufmendes.noteapp.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yusufmendes.noteapp.R
import com.yusufmendes.noteapp.databinding.FragmentNoteDetailBinding
import com.yusufmendes.noteapp.model.Note

class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

    private lateinit var binding: FragmentNoteDetailBinding
    private lateinit var refUsers: DatabaseReference
    private val args: NoteDetailFragmentArgs by navArgs()
    private var note: Note? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNoteDetailBinding.bind(view)

        val database = FirebaseDatabase.getInstance()
        refUsers = database.getReference("note")

        note = args.note

        if (note == null) {
            binding.detailScreenUpdateButton.visibility = View.GONE
            binding.detailScreenSaveButton.visibility = View.VISIBLE
            binding.detailScreenSaveButton.setOnClickListener {
                findNavController().popBackStack()
                saveNote()
            }
        } else {
            binding.detailScreenUpdateButton.visibility = View.VISIBLE
            binding.detailScreenSaveButton.visibility = View.GONE
            binding.detailScreenNoteTitle.setText(note!!.noteTitle)
            binding.detailScreenNoteDetail.setText(note!!.noteDetail)

            binding.detailScreenUpdateButton.setOnClickListener {
                findNavController().popBackStack()
                updateNote(note!!)
            }
        }
    }

    private fun updateNote(note: Note) {

        val title = binding.detailScreenNoteTitle.text.toString().trim()
        val detail = binding.detailScreenNoteDetail.text.toString().trim()

        val noteHashMap = HashMap<String, Any>()
        noteHashMap.put("noteTitle", title)
        noteHashMap.put("noteDetail", detail)
        note.noteId?.let { refUsers.child(it).updateChildren(noteHashMap) }

        Toast.makeText(requireContext(), "$title - $detail", Toast.LENGTH_SHORT).show()
    }

    private fun saveNote() {
        val title = binding.detailScreenNoteTitle.text.toString().trim()
        val detail = binding.detailScreenNoteDetail.text.toString().trim()
        val referance = refUsers.push()
        val note = Note(referance.key, title, detail)
        referance.setValue(note)
        Toast.makeText(requireContext(), "$title - $detail", Toast.LENGTH_SHORT).show()
    }
}