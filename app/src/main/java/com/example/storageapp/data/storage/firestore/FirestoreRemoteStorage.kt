package com.example.storageapp.data.storage.firestore

import com.example.storageapp.data.mapper.NoteMapper
import com.example.storageapp.domain.model.NoteModel
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FirestoreRemoteStorage @Inject constructor (
    private val firestore: FirebaseFirestore,
    private val mapper: NoteMapper,
) {

    fun getNotes(): Observable<List<NoteModel>> {
        return Observable.create { emitter ->
            firestore.collection(COLLECTION)
                .addSnapshotListener { value, error ->
                    if (value != null) emitter.onNext(value)
                    else if (error != null) emitter.onError(error)
                }
        }.map { notes -> notes.map { note -> mapper.mapCollection(note.id, note.data) } }
    }

    fun updateNote(noteId: String, title: String, content: String): Completable {
        return Completable
            .create { emitter ->
                firestore.collection(COLLECTION).document(noteId)
                    .update(TITLE, title, CONTENT, content)
                    .addOnSuccessListener {
                        emitter.onComplete()
                    }
                    .addOnFailureListener { emitter.onError(FirestoreRemoteStorageExceptions.UpdateNoteException) }
            }
    }

    fun getNote(noteId: String): Observable<NoteModel> {
        return Observable.create { emitter ->
            firestore.collection(COLLECTION).document(noteId)
                .addSnapshotListener { value, error ->
                    if (value != null && value.data != null) {
                        emitter.onNext(mapper.mapCollection(value.id, value.data!!))
                    } else {
                        emitter.onError(FirestoreRemoteStorageExceptions.GetNoteException)
                    }
                }
        }
    }

    fun setNote(id: String, title: String, content: String): Observable<NoteModel> {
        return Observable.create { emitter ->
            firestore.collection(COLLECTION).document(id)
                .set(mapOf(TITLE to title, CONTENT to content))
                .addOnSuccessListener { emitter.onNext(NoteModel(id,title, content))}
                .addOnFailureListener { FirestoreRemoteStorageExceptions.AddNoteException }
        }
    }

    fun addNote(title: String, content: String): Single<String> {
        return Single.create { emitter ->
            firestore.collection(COLLECTION)
                .add(mapOf(TITLE to title, CONTENT to content))
                .addOnSuccessListener { noteReference ->
                    emitter.onSuccess(noteReference.id)
                }
                .addOnFailureListener { emitter.onError(FirestoreRemoteStorageExceptions.AddNoteException) }
        }
    }


    fun deleteNote(noteId: String): Completable {
        return Completable.create { emitter ->
            firestore.collection(COLLECTION).document(noteId)
                .delete()
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(FirestoreRemoteStorageExceptions.DeleteNoteException) }
        }
    }

    companion object {
        const val COLLECTION = "notes"
        const val TITLE = "title"
        const val CONTENT = "content"
    }

    sealed class FirestoreRemoteStorageExceptions() : Exception() {
        object GetNoteException : FirestoreRemoteStorageExceptions()
        object AddNoteException : FirestoreRemoteStorageExceptions()
        object DeleteNoteException : FirestoreRemoteStorageExceptions()
        object UpdateNoteException : FirestoreRemoteStorageExceptions()

    }
}


