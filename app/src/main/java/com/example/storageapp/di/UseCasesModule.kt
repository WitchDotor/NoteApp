package com.example.storageapp.di

import com.example.storageapp.domain.boundaries.repository.NoteRepository
import com.example.storageapp.domain.use_case.AddNoteUseCase
import com.example.storageapp.domain.use_case.DeleteNoteUseCase
import com.example.storageapp.domain.use_case.GetNoteUseCase
import com.example.storageapp.domain.use_case.GetNotesUseCase
import com.example.storageapp.domain.use_case.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideAddNoteUseCase(noteRepository: NoteRepository) = AddNoteUseCase(noteRepository)

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository) = DeleteNoteUseCase(noteRepository)

    @Provides
    @Singleton
    fun provideGetNotesUseCase(noteRepository: NoteRepository) = GetNotesUseCase(noteRepository)

    @Provides
    @Singleton
    fun provideGetNoteUseCase(noteRepository: NoteRepository) = GetNoteUseCase(noteRepository)

    @Provides
    @Singleton
    fun provideUpdateNoteUseCase(noteRepository: NoteRepository) = UpdateNoteUseCase(noteRepository)

}