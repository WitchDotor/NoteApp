package com.example.storageapp.data.storage.room.dao

import androidx.room.*
import com.example.storageapp.data.storage.room.entity.NoteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

// Data Access Object (DAO) for the NoteEntity class. This interface will be used by Room to generate the code needed to access the database.
@Dao
interface NoteDao {
//    @Update
//    @Insert
//    @Query
//    @Delete


    @Upsert
    fun updateNotes(notes:List<NoteEntity>): Completable

    @Insert
    fun insert(note: NoteEntity): Completable

    @Update(entity = NoteEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: NoteEntity): Completable

    @Query("DELETE FROM notes WHERE id=:noteId")
    fun delete(noteId: String): Completable

    // Select (id) FROM notes
    // Select (title, content) FROM notes
    @Query("SELECT * FROM notes")
    fun getNotes(): Observable<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNote(noteId: String): Observable<NoteEntity>

}