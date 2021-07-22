package ru.lachesis.nasapictures.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.lachesis.nasapictures.R
import ru.lachesis.nasapictures.databinding.NotesItemBinding

class NotesAdapter(
    val noteItemClickListener: onNoteItemClickListener,
    val items: MutableList<Note>
    ) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    lateinit var binding: NotesItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notes_item, parent, false)
        binding = NotesItemBinding.bind(itemView)
        NotesItemBinding.bind(itemView)
        return NotesViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return  items.size
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(items.get(position))
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            binding.noteHeader.text = note.header
            binding.noteContent.text = note.text
            binding.addNote.setOnClickListener { addItem() }
            binding.dropNote.setOnClickListener { dropItem() }
            binding.arrowUp.setOnClickListener { itemUp() }
            binding.arrowDown.setOnClickListener { itemDown() }
            binding.viewContent.setOnClickListener{ showText(itemView)}
            itemView.setOnClickListener { noteItemClickListener.onItemClick(note) }
        }

        private fun showText(view: View) {
            view.findViewById<TextView>(R.id.note_content).apply {
                if (visibility== View.VISIBLE)
                    visibility = View.GONE
                else visibility= View.VISIBLE
            }
            notifyDataSetChanged()
//            notifyItemChanged(layoutPosition)

        }

        private fun itemDown() {
            if (layoutPosition < items.size-1) {
                val note = items.get(layoutPosition)
                items.removeAt(layoutPosition)
                items.add(layoutPosition + 1, note)
                notifyItemMoved(layoutPosition, layoutPosition + 1)
            }
        }

        private fun itemUp() {
            if (layoutPosition > 0) {
                val note = items.get(layoutPosition)
                items.removeAt(layoutPosition)
                items.add(layoutPosition - 1, note)
                notifyItemMoved(layoutPosition - 1, layoutPosition)
            }

        }

        private fun dropItem() {
            items.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun addItem() {
            items.add(Note("Заметка", "texttexttext"))
            notifyItemInserted(layoutPosition)

        }
    }

}
