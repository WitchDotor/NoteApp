package com.example.storageapp.presentation.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.storageapp.presentation.model.NoteDisplayModel
import com.example.storageapp.presentation.notes.recycler.NoteViewHolder

class NoteDiffUtils : DiffUtil.ItemCallback<NoteDisplayModel>() {
    override fun areItemsTheSame(oldItem: NoteDisplayModel, newItem: NoteDisplayModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NoteDisplayModel, newItem: NoteDisplayModel): Boolean =
        oldItem == newItem
}

class NotesAdapter(
    private val onNoteClick: (NoteDisplayModel) -> Unit,
    private val onLongNoteClick: (NoteDisplayModel) -> Unit,
    private val onCheckBoxClick: (NoteDisplayModel) -> Unit
) : ListAdapter<NoteDisplayModel, NoteViewHolder>(NoteDiffUtils()) {
    private var deleteState = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(parent, onNoteClick, onCheckBoxClick, onLongNoteClick)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = if (!deleteState) {
        holder.bind(getItem(position))
        holder.hideCheckBox()
        holder.check(false)
    } else {
        holder.bind(getItem(position))
        holder.showCheckBox()
    }


    fun setDeleteState(deleteState: Boolean) {
        this.deleteState = deleteState
        notifyDataSetChanged()
    }
}

