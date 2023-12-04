package com.example.storageapp.domain.use_case

import com.example.storageapp.domain.boundaries.repository.NoteRepository
import com.example.storageapp.domain.model.NoteModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetNotesUseCase (
    private val noteRepository: NoteRepository,
) {
    operator fun invoke(): Observable<List<NoteModel>> = noteRepository.getNotes()
}