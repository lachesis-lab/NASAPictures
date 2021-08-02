package ru.lachesis.nasapictures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.lachesis.nasapictures.databinding.NotesFragmentBinding

class NotesFragment : Fragment() {
    var notes: MutableList<Note> = mutableListOf(Note("Заметка", "ТекстТекстТекстТекстТекст"))
    private val listener: onNoteItemClickListener by lazy {
        object : onNoteItemClickListener {
            override fun onItemClick(note: Note) {
                Toast.makeText(requireActivity(), "${note.header} ${note.text}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private var _binding: NotesFragmentBinding? = null
    private val binding: NotesFragmentBinding get() = _binding!!
    private val adapter: NotesAdapter by lazy { NotesAdapter(listener, notes) }

    companion object {
        fun newInstance(): NotesFragment = NotesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = NotesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notesPlusFab.setOnClickListener {
            notes.add(Note("Заметка", "ТекстТекстТекстТекстТекст"))
            adapter.notifyDataSetChanged()
        }
        binding.notesBackFab.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
        binding.noteRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

interface onNoteItemClickListener {
    fun onItemClick(note: Note)
}


data class Note(
    val header: String,
    val text: String,
)