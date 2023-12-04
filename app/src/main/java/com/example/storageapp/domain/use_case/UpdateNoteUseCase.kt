package com.example.storageapp.domain.use_case

import com.example.storageapp.domain.boundaries.repository.NoteRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class UpdateNoteUseCase (
    private val noteRepository: NoteRepository,
) {
    operator fun invoke(noteId: String, title: String, content: String): Completable {
        return noteRepository.updateNote(noteId, title, content)
    }
}