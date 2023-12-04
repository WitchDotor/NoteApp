package com.example.storageapp.data.storage.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "content") val content: String?,
)

// INSERT INTO notes (id, title, content, content_2) VALUES ('1', 'title', 'content', 'content2')
// SELECT (title, content) FROM notes WHERE id = '1'