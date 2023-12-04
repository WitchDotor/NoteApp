package com.example.storageapp.data.storage.room

import com.example.storageapp.data.mapper.NoteMapper
import com.example.storageapp.data.storage.room.database.AppDatabase
import com.example.storageapp.data.storage.room.entity.NoteEntity
import com.example.storageapp.domain.model.NoteModel
import javax.inject.Inject

class RoomLocalStorage @Inject constructor(
    private val database: AppDatabase,
    private val mapper: NoteMapper,
) {

    fun updateNotes(notes: List<NoteModel>) =
        database.noteDao().updateNotes(mapper.mapModelListToEntityList(notes))


    fun addNote(id: String, title: String, content: String) =
        database.noteDao().insert(mapper.mapModelToEntity(NoteModel(id, title, content)))

    fun updateNote(note: NoteModel) = database.noteDao().update(mapper.mapModelToEntity(note))

    fun deleteNote(noteId: String) = database.noteDao().delete(noteId)
    fun getNotes() = database.noteDao().getNotes().map { it.map { mapper.mapEntityToModel(it) } }
    fun getNote(noteId: String) =
        database.noteDao().getNote(noteId).map { mapper.mapEntityToModel(it) }
}