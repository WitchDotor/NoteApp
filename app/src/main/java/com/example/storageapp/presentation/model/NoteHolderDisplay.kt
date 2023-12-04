package com.example.storageapp.presentation.model


import com.example.storageapp.domain.model.NoteModel


class NoteHolderDisplay {

    fun mapNoteToNoteDisplay(model: NoteModel, check: Boolean) = NoteDisplayModel(
        id = model.id,
        title = model.title,
        isChecked = check
    )

    fun mapNoteListToNoteDisplayList(noteList: List<NoteModel>, check: Boolean) =
        noteList.map { mapNoteToNoteDisplay(it, check) }
}

