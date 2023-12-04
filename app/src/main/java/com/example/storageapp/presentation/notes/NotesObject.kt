package com.example.storageapp.presentation.notes

import com.example.storageapp.presentation.model.NoteDisplayModel

object NotesObject {
    interface View {
        fun showLoading()
        fun showNotes(notes: List<NoteDisplayModel>)
        fun showError()
        fun showDeleteSucces()
        fun navigateToNoteActivity(noteId: String)

        fun setDeleteState()
        fun setBasicState()
        fun setSearchState()
        fun showFailToast()

    }

    interface Presenter {
        fun loadData()
        fun deleteNote()
        fun addNote(title: String, Content: String)
        fun dispose()
        fun filterNotes(filter: String)
        fun sortNotes(filtration: String)
        fun addToDeleteSet(noteId: String)
        fun removeFromDeleteSet(noteId: String)
        fun clearDeleteSet()
        fun onLongNoteClick(note: NoteDisplayModel)
        fun onGoBackClick()
        fun onCheckBoxClick(note: NoteDisplayModel)
        fun onNoteClick(note: NoteDisplayModel)
        fun onDeleteFubClick()
    }
}