package com.example.storageapp.di

import com.example.storageapp.presentation.model.NoteHolderDisplay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NoteHolderDisplayModule {

    @Provides
    @Singleton
    fun provideNoteHolderDisplay()= NoteHolderDisplay()
}