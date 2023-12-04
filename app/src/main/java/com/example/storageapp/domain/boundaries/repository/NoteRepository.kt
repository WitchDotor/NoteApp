package com.example.storageapp.domain.boundaries.repository

import com.example.storageapp.domain.model.NoteModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface NoteRepository {

    fun getNotes(): Observable<List<NoteModel>>
    fun getNote(noteId: String): Observable<NoteModel>
    fun addNote(title: String, content: String): Single<String>
    fun updateNote(noteId: String, title: String, content: String): Completable
    fun deleteNote(noteId: String): Completable
}