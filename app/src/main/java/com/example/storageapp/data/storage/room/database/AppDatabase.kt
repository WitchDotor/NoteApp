package com.example.storageapp.data.storage.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.storageapp.data.storage.room.dao.NoteDao
import com.example.storageapp.data.storage.room.entity.NoteEntity

@Database(
    version = 1,
    entities = [
        NoteEntity::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {

        fun build(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database",
        )
            .fallbackToDestructiveMigration()
            //.allowMainThreadQueries()
            .build()
    }
}