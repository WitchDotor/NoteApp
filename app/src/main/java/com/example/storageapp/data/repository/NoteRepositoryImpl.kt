package com.example.storageapp.data.repository

import android.util.Log
import com.example.storageapp.data.storage.firestore.FirestoreRemoteStorage
import com.example.storageapp.data.storage.room.RoomLocalStorage
import com.example.storageapp.domain.boundaries.repository.NoteRepository
import com.example.storageapp.domain.model.NoteModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class NoteRepositoryImpl @Inject constructor(
    private val firestoreRemoteStorage: FirestoreRemoteStorage,
    private val roomLocalStorage: RoomLocalStorage,
) : NoteRepository {

    override fun getNotes(): Observable<List<NoteModel>> {
        return firestoreRemoteStorage.getNotes()
            .flatMap {
                roomLocalStorage.updateNotes(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .andThen(firestoreRemoteStorage.getNotes())
            }
    }

    override fun getNote(noteId: String): Observable<NoteModel> {
        return firestoreRemoteStorage.getNote(noteId)
    }

    override fun addNote(title: String, content: String): Single<String> {
        return firestoreRemoteStorage.addNote(title, content)
            
    }


    override fun updateNote(noteId: String, title: String, content: String): Completable {
        return firestoreRemoteStorage.updateNote(noteId, title, content)
    }

    override fun deleteNote(noteId: String): Completable {
        return firestoreRemoteStorage.deleteNote(noteId)
            .andThen(
                roomLocalStorage.deleteNote(noteId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
            )
    }
}