package com.example.storageapp.presentation.notes.recycler

import com.example.storageapp.presentation.model.NoteDisplayModel

interface NoteViewHolderInterface {

        fun bind(note: NoteDisplayModel)
        fun showCheckBox()
        fun hideCheckBox()
        fun check(check: Boolean)
        fun getNoteId(note: NoteDisplayModel):String

}