package com.example.storageapp.presentation.note

object NoteObject {

    interface View {
        fun navigateToNotesActivity()

        fun initializeNote(title: String, content: String)
        fun showLoading()
        fun showNote()
        fun showError()
        fun showUpdateError()
        fun showUpdateSucces()
        fun showGetError()

    }

    interface Presenter {

        fun getNote(noteId: String)
        fun updateNote(title: String, content: String)
        fun dispose()
    }
}