package com.example.storageapp.presentation.notes.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.storageapp.R
import com.example.storageapp.databinding.ItemNoteBinding
import com.example.storageapp.presentation.model.NoteDisplayModel
import com.example.storageapp.presentation.notes.NotesAdapter

class NoteViewHolder(
    parent: ViewGroup,
    private val onClick: (NoteDisplayModel) -> Unit,
    private val onCheckBoxClick: (NoteDisplayModel) -> Unit,
    private val onLongNoteClick: (NoteDisplayModel) -> Unit,

) : ViewHolder(parent.inflate(R.layout.item_note)),
    NoteViewHolderInterface {

    private val binding = ItemNoteBinding.bind(itemView)

    override fun bind(note: NoteDisplayModel) {
        binding.textTitle.text = note.title

        binding.root.setOnClickListener { onClick(note) }
        binding.root.setOnLongClickListener {
            onLongNoteClick(note)
            return@setOnLongClickListener true
        }
        binding.selectionButton.setOnClickListener {
            note.isChecked = binding.selectionButton.isChecked
            onCheckBoxClick(note)
        }
    }


    override fun getNoteId(note: NoteDisplayModel): String {
        return note.id
    }

    override fun showCheckBox() {
        binding.selectionButton.isVisible = true
    }

    override fun hideCheckBox() {
        binding.selectionButton.isVisible = false
    }

    override fun check(check: Boolean) {
        binding.selectionButton.isChecked = check
    }
}


fun ViewGroup.inflate(@LayoutRes id: Int) = LayoutInflater.from(context).inflate(id, this, false)