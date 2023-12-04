package com.example.storageapp.di

import android.content.Context
import com.example.storageapp.data.mapper.NoteMapper
import com.example.storageapp.data.storage.firestore.FirestoreRemoteStorage
import com.example.storageapp.data.storage.room.database.AppDatabase
import com.example.storageapp.data.storage.room.entity.NoteEntity
import com.example.storageapp.domain.boundaries.repository.NoteRepository
import com.example.storageapp.domain.model.NoteModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoteRepositoryImpProvider {
    @Provides
    @Singleton
    fun provideNoteMapper() = NoteMapper()

    @Provides
    @Singleton
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.build(context)

}


