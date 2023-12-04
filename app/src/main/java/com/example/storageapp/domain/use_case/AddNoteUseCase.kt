package com.example.storageapp.domain.use_case

import com.example.storageapp.domain.boundaries.repository.NoteRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AddNoteUseCase(
    private val noteRepository: NoteRepository,
) {
    operator fun invoke(title: String, content: String): Single<String> {
        return noteRepository.addNote(title, content)
    }
}