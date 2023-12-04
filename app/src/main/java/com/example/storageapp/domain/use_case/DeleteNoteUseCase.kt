package com.example.storageapp.domain.use_case

import com.example.storageapp.domain.boundaries.repository.NoteRepository
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class DeleteNoteUseCase(
    private val noteRepository: NoteRepository,
) {
    operator fun invoke(noteId: String): Completable {
        return noteRepository.deleteNote(noteId)
    }
}