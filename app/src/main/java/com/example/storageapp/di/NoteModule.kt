package com.example.storageapp.di

import android.content.Context
import com.example.storageapp.presentation.note.NoteActivity
import com.example.storageapp.presentation.note.NoteObject
import com.example.storageapp.presentation.note.NotePresenter
import com.example.storageapp.presentation.notes.NotesActivity
import com.example.storageapp.presentation.notes.NotesObject
import com.example.storageapp.presentation.notes.NotesPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
abstract class NotePresenterModule {
    @Binds
    abstract fun provideNoteActivity(impl: NoteActivity): NoteObject.View

    @Binds
    abstract fun provideNotePresenter(view: NotePresenter): NoteObject.Presenter

    companion object NoteActivityModule {
        @Provides
        fun bindActivity(@ActivityContext context: Context): NoteActivity = context as NoteActivity
    }

}

