package com.example.storageapp.di

import com.example.storageapp.data.repository.NoteRepositoryImpl
import com.example.storageapp.domain.boundaries.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface NoteRepositoryModule {
    @Binds
    fun provideNoteRepository(imp: NoteRepositoryImpl): NoteRepository
}